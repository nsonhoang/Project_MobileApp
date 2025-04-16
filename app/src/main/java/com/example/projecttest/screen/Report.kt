package com.example.projecttest.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projecttest.databinding.FragmentReportBinding

class Report : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    private var age = 30
    private var weight = 78
    private var height = 175 // cm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial values
        binding.txttuoi.text = age.toString()
        binding.txtCanNang.text = weight.toString()
        binding.tvHeight.text = height.toString()

        // Tuổi
        binding.btnaddage.setOnClickListener {
            age++
            binding.txttuoi.text = age.toString()
        }
        binding.btnminusage.setOnClickListener {
            if (age > 1) age--
            binding.txttuoi.text = age.toString()
        }

        // Cân nặng
        binding.btnaddkg.setOnClickListener {
            weight++
            binding.txtCanNang.text = weight.toString()
        }
        binding.btnminuskg.setOnClickListener {
            if (weight > 1) weight--
            binding.txtCanNang.text = weight.toString()
        }

        // Chiều cao
        binding.seekBar.setOnSeekBarChangeListener(object :
            android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                height = progress
                binding.tvHeight.text = height.toString()
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        // Tính BMI
        binding.button.setOnClickListener {
            val weight = binding.txtCanNang.text.toString().toFloatOrNull()
            val heightCm = binding.tvHeight.text.toString().toFloatOrNull()

            if (weight != null && heightCm != null) {
                val heightM = heightCm / 100
                val bmi = weight / (heightM * heightM)
                val formattedBmi = String.format("%.1f", bmi) //làm tròn đến 1 chữ số thập phân
                binding.txtbmi.text = formattedBmi

                // Phân loại tình trạng cơ thể
                val status = when {
                    bmi < 18.5 -> "Nhẹ cân"
                    bmi < 25 -> "Bình thường"
                    bmi < 30 -> "Thừa cân"
                    bmi < 35 -> "Béo phì độ I"
                    else -> "Béo phì độ II"
                }

                binding.txtBmiStatus.text = "Tình trạng: $status"
            } else {
                Toast.makeText(context, "Vui lòng nhập cân nặng và chiều cao hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
