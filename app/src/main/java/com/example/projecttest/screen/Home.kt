package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projecttest.databinding.FragmentHomeBinding
import com.example.projecttest.Data.KieuBaiTap
import com.example.projecttest.Data.WorkoutProgram
import com.example.projecttest.screen.adapter.WorkoutAdapter
import com.example.projecttest.R
import com.example.projecttest.screen.adapter.WorkoutProgramAdapter
import com.example.projecttest.screen.courses.CourseDetail

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
        TWadapter = WorkoutAdapter(workoutList)
        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TWadapter
        }
        WPadapter = WorkoutProgramAdapter(workoutPrograms)
        binding.rvWorkPrograms.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = WPadapter
        }
        val chuyenThuThach: ImageView= binding.root.findViewById(R.id.ivThumbnail)
        chuyenThuThach.setOnClickListener {
            switchCourseDetail()
        }
        val chuyenMucDo: ImageView = binding.root.findViewById(R.id.imgWorkout)
        chuyenMucDo.setOnClickListener {
            switchCourseDetail()
        }
        val chuyenLich: LinearLayout= binding.root.findViewById(R.id.training_info)
        chuyenLich.setOnClickListener {
            switchLich()
        }
        return binding.root
    }
    private fun switchCourseDetail(){
        val intent= Intent(requireContext(), CourseDetail::class.java)
    }
    private fun switchLich(){
//        val intent = Intent(requireContext(), Lich::class.java)
    }
}