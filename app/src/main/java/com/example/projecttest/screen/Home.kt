
package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projecttest.data.Course
import com.example.projecttest.viewmodel.CourseViewModel
import kotlinx.coroutines.launch

class Home : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var twAdapter: WorkoutAdapter
    private lateinit var wpAdapter: WorkoutProgramAdapter
    private val userSummaryViewModel: UserSummaryViewModel by viewModels()
    private lateinit var courseViewModel: CourseViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java) // gọi viewModel

        lifecycleScope.launch {
            try {
                courseViewModel.fetchDataListHome() // lấy dữ liệu

                courseViewModel.trainingProgramListHome.collect {

                        createItemListHome(it) /// hàm sửa lí việc in danh sách ra
                }


            }catch (e: Exception) {
                // Xử lý lỗi ở đây
                println("Error: ${e.message}")
            }
        }

        val workoutPrograms = listOf(
            WorkoutProgram("Toàn thân thử thách 7x4", 50, R.drawable.workout1),
            WorkoutProgram("Thử thách cơ bụng", 30, R.drawable.workout2),
        )

//        val workoutList = listOf(
//            KieuBaiTap("Bụng người bắt đầu", "20 PHÚT", "16 Bài Tập", R.drawable.bungnewbie, "Người bắt đầu"),
//            KieuBaiTap("Ngực người bắt đầu", "9 PHÚT", "11 Bài Tập", R.drawable.ngucnewbie, "Người bắt đầu"),
//            KieuBaiTap("Cánh tay người bắt đầu", "17 PHÚT", "19 Bài Tập", R.drawable.taynewbie, "Người bắt đầu"),
//            KieuBaiTap("Chân người bắt đầu", "26 PHÚT", "23 Bài Tập", R.drawable.channewbie, "Người bắt đầu"),
//            KieuBaiTap("Bụng trung bình", "22 PHÚT", "12 Bài Tập", R.drawable.bungavg, "Trung bình"),
//            KieuBaiTap("Ngực trung bình", "11 PHÚT", "14 Bài Tập", R.drawable.ngucavg, "Trung bình"),
//            KieuBaiTap("Cánh tay trung bình", "19 PHÚT", "12 Bài Tập", R.drawable.tayavg, "Trung bình"),
//            KieuBaiTap("Chân trung bình", "28 PHÚT", "14 Bài Tập", R.drawable.chanavg, "Trung bình"),
//            KieuBaiTap("Bụng nâng cao", "25 PHÚT", "18 Bài Tập", R.drawable.bunghight, "Nâng cao"),
//            KieuBaiTap("Ngực nâng cao", "13 PHÚT", "15 Bài Tập", R.drawable.nguchight, "Nâng cao"),
//            KieuBaiTap("Cánh tay nâng cao", "21 PHÚT", "23 Bài Tập", R.drawable.tayhight, "Nâng cao"),
//            KieuBaiTap("Chân nâng cao", "30 PHÚT", "27 Bài Tập", R.drawable.chanhight, "Nâng cao")
//        )
        userSummaryViewModel.fetchUserSummary("CmdNGAdOkVaFqdVHD9ISsbPCZHa2")
        // Truyền sự kiện click vào Adapter

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
        observeUserSummary()





        return binding.root
    }

    private fun createItemListHome(ds: List<Course>) {
        val list = createListWorkOut(ds)// tạo ds cho list bài tập
        setAdapter(list)

    }


    private fun observeUserSummary() {
        userSummaryViewModel.userSummary.observe(viewLifecycleOwner) { summary ->
            summary?.let {
                // 🔥 Khi có dữ liệu sẽ tự động cập nhật giao diện
                binding.txtCountTraining.text = "${it.trainingCount}\n LẦN TẬP"
                binding.txtKcal.text = "${it.kcalCount}\n KCAL"
                binding.txtTimeTraining.text = "${it.timeTraining}\n PHÚT"
            }
        }
    }
    private fun createListWorkOut(list: List<Course>) :MutableList<Course> {
        val ds = mutableListOf<Course>()
        list.forEach { course ->
            ds.add(
                Course(
                    course.name,
                    course.detail,
                    course.level,
                    course.totalTime,
                    course.img,
                    course.modules
                )
            )
        }

        return ds
    }

    private fun setAdapter(ds: List<Course>){
        twAdapter = WorkoutAdapter(ds, this@Home)
        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = twAdapter
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
        transaction.replace(R.id.ltongKet, Report()) // Đảm bảo R.id.fragment_container đúng
        transaction.addToBackStack(null) // Cho phép quay lại fragment trước đó
        transaction.commit()
    }

}
