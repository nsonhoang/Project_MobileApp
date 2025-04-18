
package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projecttest.R
import com.example.projecttest.data.KieuBaiTap
import com.example.projecttest.data.WorkoutProgram
import com.example.projecttest.databinding.FragmentHomeBinding
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.adapter.WorkoutAdapter
import com.example.projecttest.screen.adapter.WorkoutProgramAdapter
import com.example.projecttest.screen.courses.Courses
import com.example.projecttest.model.UserSummaryViewModel
import androidx.fragment.app.viewModels

class Home : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var twAdapter: WorkoutAdapter
    private lateinit var wpAdapter: WorkoutProgramAdapter
    private lateinit var txtCountTraining : TextView
    private lateinit var txtKcal : TextView
    private lateinit var txtTimeTraining : TextView
    private val userSummaryViewModel: UserSummaryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val workoutPrograms = listOf(
            WorkoutProgram("Toàn thân thử thách 7x4", 50, R.drawable.workout1),
            WorkoutProgram("Thử thách cơ bụng", 30, R.drawable.workout2),
        )
        val workoutList = listOf(
            KieuBaiTap("Bụng người bắt đầu", "20 PHÚT", "16 Bài Tập", R.drawable.bungnewbie, "Người bắt đầu"),
            KieuBaiTap("Ngực người bắt đầu", "9 PHÚT", "11 Bài Tập", R.drawable.ngucnewbie, "Người bắt đầu"),
            KieuBaiTap("Cánh tay người bắt đầu", "17 PHÚT", "19 Bài Tập", R.drawable.taynewbie, "Người bắt đầu"),
            KieuBaiTap("Chân người bắt đầu", "26 PHÚT", "23 Bài Tập", R.drawable.channewbie, "Người bắt đầu"),
            KieuBaiTap("Bụng trung bình", "22 PHÚT", "12 Bài Tập", R.drawable.bungavg, "Trung bình"),
            KieuBaiTap("Ngực trung bình", "11 PHÚT", "14 Bài Tập", R.drawable.ngucavg, "Trung bình"),
            KieuBaiTap("Cánh tay trung bình", "19 PHÚT", "12 Bài Tập", R.drawable.tayavg, "Trung bình"),
            KieuBaiTap("Chân trung bình", "28 PHÚT", "14 Bài Tập", R.drawable.chanavg, "Trung bình"),
            KieuBaiTap("Bụng nâng cao", "25 PHÚT", "18 Bài Tập", R.drawable.bunghight, "Nâng cao"),
            KieuBaiTap("Ngực nâng cao", "13 PHÚT", "15 Bài Tập", R.drawable.nguchight, "Nâng cao"),
            KieuBaiTap("Cánh tay nâng cao", "21 PHÚT", "23 Bài Tập", R.drawable.tayhight, "Nâng cao"),
            KieuBaiTap("Chân nâng cao", "30 PHÚT", "27 Bài Tập", R.drawable.chanhight, "Nâng cao")
        )
        userSummaryViewModel.fetchUserSummary("lRKX1O9ZDAHh2lrVwSHi")
        // Truyền sự kiện click vào Adapter
        twAdapter = WorkoutAdapter(workoutList, this)
        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = twAdapter
        }

        wpAdapter = WorkoutProgramAdapter(workoutPrograms) { selectedProgram ->
            Log.d("HomeFragment", "Đã nhấn vào: ${selectedProgram.tenbaitap}")
            val intent = Intent(requireContext(), Courses::class.java).apply {
                putExtra("PROGRAM_NAME", selectedProgram.tenbaitap)
            }
            startActivity(intent)
        }

        binding.rvWorkPrograms.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = wpAdapter
        }

        wpAdapter.notifyDataSetChanged()
        binding.trainingInfo.setOnClickListener {
            switchLich()
        }

        return binding.root
    }
    private fun observeUserSummary() {
        userSummaryViewModel.userSummary.observe(viewLifecycleOwner) { summary ->
            summary?.let {
                // 🔥 Khi có dữ liệu sẽ tự động cập nhật giao diện
                binding.txtCountTraining.text = it.trainingCount.toString()
                binding.txtKcal.text = it.kcalCount.toString()
                binding.txtTimeTraining.text = it.timeTraining
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
        transaction.replace(R.id.fragment_container, Report()) // Đảm bảo R.id.fragment_container đúng
        transaction.addToBackStack(null) // Cho phép quay lại fragment trước đó
        transaction.commit()
    }

}
