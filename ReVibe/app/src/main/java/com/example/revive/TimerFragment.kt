package com.example.revive

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.revive.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private var running = false
    private var baseTime: Long = 0
    private var pauseOffset: Long = 0

    private val lapTimes = mutableListOf<Long>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener {
            if (!running) {
                baseTime = SystemClock.elapsedRealtime() - pauseOffset
                binding.chronometer.base = baseTime
                binding.chronometer.start()
                running = true
            }
        }

        binding.buttonStop.setOnClickListener {
            if (running) {
                binding.chronometer.stop()
                pauseOffset = SystemClock.elapsedRealtime() - binding.chronometer.base
                running = false
            }
        }

        binding.buttonLap.setOnClickListener {
            if (running) {
                val lapTime = SystemClock.elapsedRealtime() - binding.chronometer.base
                lapTimes.add(lapTime)
                updateLapTimes()
            }
        }
    }

    private fun updateLapTimes() {
        val lapText = StringBuilder()
        lapTimes.forEachIndexed { index, time ->
            val seconds = (time / 1000) % 60
            val minutes = (time / 1000) / 60
            lapText.append("Lap ${index + 1}: %02d:%02d\n".format(minutes, seconds))
        }
        binding.textViewLaps.text = lapText.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
