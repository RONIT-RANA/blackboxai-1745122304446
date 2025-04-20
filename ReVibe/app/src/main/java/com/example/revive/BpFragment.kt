package com.example.revive

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.revive.databinding.FragmentBpBinding

class BpFragment : Fragment() {

    private var _binding: FragmentBpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCheckBp.setOnClickListener {
            val systolicStr = binding.editTextSystolic.text.toString()
            val diastolicStr = binding.editTextDiastolic.text.toString()

            if (TextUtils.isEmpty(systolicStr) || TextUtils.isEmpty(diastolicStr)) {
                Toast.makeText(requireContext(), "Please enter both systolic and diastolic values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val systolic = systolicStr.toIntOrNull()
            val diastolic = diastolicStr.toIntOrNull()

            if (systolic == null || diastolic == null || systolic <= 0 || diastolic <= 0) {
                Toast.makeText(requireContext(), "Please enter valid blood pressure values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val category = getBpCategory(systolic, diastolic)
            binding.textViewBpResult.text = "Your BP Category: $category"
            binding.textViewBpAdvice.text = getAdvice(category)
        }
    }

    private fun getBpCategory(systolic: Int, diastolic: Int): String {
        return when {
            systolic < 90 || diastolic < 60 -> "Low Blood Pressure"
            systolic in 90..120 && diastolic in 60..80 -> "Normal Blood Pressure"
            systolic in 121..139 || diastolic in 81..89 -> "Prehypertension"
            systolic >= 140 || diastolic >= 90 -> "High Blood Pressure"
            else -> "Unknown"
        }
    }

    private fun getAdvice(category: String): String {
        return when (category) {
            "Low Blood Pressure" -> "Low BP: Consider exercise, dietary changes, and consult a medical professional."
            "Normal Blood Pressure" -> "Normal BP: Maintain your healthy lifestyle."
            "Prehypertension" -> "Prehypertension: Monitor regularly, consider lifestyle changes."
            "High Blood Pressure" -> "High BP: Mandatory exercise, dietary changes, and consult a medical professional."
            else -> ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
