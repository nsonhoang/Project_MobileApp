package com.example.projecttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.databinding.FragmentHomeBinding

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var TWadapter: WorkoutAdapter
    private lateinit var WPadapter: WorkoutProgramAdapter

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
            KieuBaiTap("Bụng người bắt đầu", "20 PHÚT", 16, R.drawable.bungnewbie, "Người bắt đầu"),
            KieuBaiTap("Ngực người bắt đầu", "9 PHÚT", 11, R.drawable.ngucnewbie, "Người bắt đầu"),
            KieuBaiTap("Cánh tay người bắt đầu", "17 PHÚT", 19, R.drawable.taynewbie, "Người bắt đầu"),
            KieuBaiTap("Chân người bắt đầu", "26 PHÚT", 23, R.drawable.channewbie, "Người bắt đầu"),
            KieuBaiTap("Bụng trung bình", "22 PHÚT", 18, R.drawable.bungavg, "Trung bình"),
            KieuBaiTap("Ngực trung bình", "11 PHÚT", 13, R.drawable.ngucavg, "Trung bình"),
            KieuBaiTap("Cánh tay trung bình", "19 PHÚT", 21, R.drawable.tayavg, "Trung bình"),
            KieuBaiTap("Chân trung bình", "28 PHÚT", 25, R.drawable.chanavg, "Trung bình"),
            KieuBaiTap("Bụng nâng cao", "25 PHÚT", 20, R.drawable.bunghight, "Nâng cao"),
            KieuBaiTap("Ngực nâng cao", "13 PHÚT", 15, R.drawable.nguchight, "Nâng cao"),
            KieuBaiTap("Cánh tay nâng cao", "21 PHÚT", 23, R.drawable.tayhight, "Nâng cao"),
            KieuBaiTap("Chân nâng cao", "30 PHÚT", 27, R.drawable.chanhight, "Nâng cao")
        )
        TWadapter = WorkoutAdapter(workoutList)
        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TWadapter
        }
        WPadapter = WorkoutProgramAdapter(workoutPrograms)
        binding.rvWorkPrograms.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = WPadapter
        }
        return binding.root
    }
}