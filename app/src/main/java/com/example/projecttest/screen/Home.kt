package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projecttest.R
import com.example.projecttest.databinding.FragmentHomeBinding
import com.example.projecttest.model.UserSummaryViewModel
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.adapter.RvAdapterMucDo
import com.example.projecttest.screen.adapter.RvAdapterThuThach
import com.example.projecttest.screen.courses.Courses
import com.example.projecttest.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class Home : Fragment(), OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mucDoAdapter: RvAdapterMucDo
    private lateinit var thuThachAdapter: RvAdapterThuThach

    private val userSummaryViewModel: UserSummaryViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Lấy userId từ Firebase Authentication
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Kiểm tra nếu userId không null
        if (userId != null) {
            // Khi vào màn hình, tự động fetch dữ liệu
            userSummaryViewModel.fetchUserSummary(userId)
            homeViewModel.fetchHomeList()
        } else {
            // Nếu không có userId, thông báo lỗi hoặc yêu cầu người dùng đăng nhập lại
            Log.e("HomeFragment", "User is not logged in")
        }

        setupRecyclerViews()
        observeUserSummary()
        observeHomeList()

        return binding.root
    }

    private fun setupRecyclerViews() {
        thuThachAdapter = RvAdapterThuThach(emptyList(), this)
        mucDoAdapter = RvAdapterMucDo(emptyList(), this)

        binding.rvWorkPrograms.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = thuThachAdapter
        }

        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mucDoAdapter
        }
    }

    private fun observeUserSummary() {
        // Lấy userId từ Firebase Authentication
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            // Quan sát thay đổi dữ liệu của UserSummary
            userSummaryViewModel.userSummary.observe(viewLifecycleOwner) { summary ->
                summary?.let {
                    // Cập nhật giao diện khi có dữ liệu
                    binding.txtCountTraining.text = "${it.trainingCount}\n LẦN TẬP"
                    binding.txtKcal.text = "${it.kcalCount}\n KCAL"
                    binding.txtTimeTraining.text = "${it.timeTraining}\n PHÚT"
                }
            }
        } else {
            // Nếu không có userId, có thể thông báo lỗi hoặc yêu cầu người dùng đăng nhập lại
            Log.e("HomeFragment", "User is not logged in")
        }
    }

    private fun observeHomeList() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.homeList.collect { homeList ->
                homeList?.let {
                    thuThachAdapter.updateList(it.flatMap { home -> home.thuThach })
                    mucDoAdapter.updateList(it.flatMap { home -> home.thuThach.flatMap { challenge -> challenge.mucDo } })
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        switchCourseDetail()
    }

    private fun switchCourseDetail() {
        val intent = Intent(requireContext(), Courses::class.java)
        startActivity(intent)

    }

    private fun switchLich() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.ltongKet, Report())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}