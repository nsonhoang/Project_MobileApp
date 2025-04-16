package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.webkit.Profile
import com.example.projecttest.databinding.FragmentSettingBinding
import com.example.projecttest.screen.setting.lang
import com.example.projecttest.screen.setting.notification
import com.example.projecttest.screen.setting.train

class Setting : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.imgbtnprofile.setOnClickListener {
            val intent = Intent(requireContext(), Profile::class.java)
            startActivity(intent)
        }

        binding.imgnoti.setOnClickListener {
            val intent = Intent(requireContext(), notification::class.java)
            startActivity(intent)
        }

        binding.imgsettrain.setOnClickListener {
            val intent = Intent(requireContext(), train::class.java)
            startActivity(intent)
        }

        binding.imglang.setOnClickListener {
            val intent = Intent(requireContext(), lang::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
