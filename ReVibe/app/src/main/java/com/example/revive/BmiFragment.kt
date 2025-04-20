package com.example.revive

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.revive.databinding.FragmentBmiBinding

class BmiFragment : Fragment() {

    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        sharedPref = requireActivity().getSharedPreferences("ReVibePrefs", 0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSavedData()

        binding.buttonCalculate.setOnClickListener {
            val heightStr = binding.editTextHeight.text.toString()
            val weightStr = binding.editTextWeight.text.toString()

            if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr)) {
                Toast.makeText(requireContext(), "Please enter height and weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val height = heightStr.toFloatOrNull()
            val weight = weightStr.toFloatOrNull()

            if (height == null || weight == null || height <= 0 || weight <= 0) {
                Toast.makeText(requireContext(), "Please enter valid height and weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bmi = calculateBmi(height, weight)
            binding.textViewBmiResult.text = "Your BMI: %.2f".format(bmi)
            binding.textViewAdvice.text = getAdvice(bmi)

            saveData(height, weight, bmi)
        }
    }

    private fun calculateBmi(heightCm: Float, weightKg: Float): Float {
        val heightM = heightCm / 100
        return weightKg / (heightM * heightM)
    }

    private fun getAdvice(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Low BMI: Increase calorie and nutrient intake through a balanced diet, exercise, and consult a medical professional."
            bmi in 18.5..24.9 -> "Normal BMI: Maintain your healthy lifestyle."
            else -> "High BMI: Consider dietary changes, regular exercise, and consult a medical professional."
        }
    }

    private fun saveData(height: Float, weight: Float, bmi: Float) {
        with(sharedPref.edit()) {
            putFloat("bmiHeight", height)
            putFloat("bmiWeight", weight)
            putFloat("bmiValue", bmi)
            apply()
        }
    }

    private fun loadSavedData() {
        val height = sharedPref.getFloat("bmiHeight", 0f)
        val weight = sharedPref.getFloat("bmiWeight", 0f)
        val bmi = sharedPref.getFloat("bmiValue", 0f)

        if (height > 0 && weight > 0) {
            binding.editTextHeight.setText(height.toString())
            binding.editTextWeight.setText(weight.toString())
        }
        if (bmi > 0) {
            binding.textViewBmiResult.text = "Your BMI: %.2f".format(bmi)
            binding.textViewAdvice.text = getAdvice(bmi)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
