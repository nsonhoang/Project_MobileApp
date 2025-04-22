package com.example.projecttest.screen.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projecttest.data.Course
import com.example.projecttest.databinding.FragmentHomeBinding
import com.example.projecttest.model.UserSummaryViewModel
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.adapter.RvAdapterThuThach
import com.example.projecttest.screen.adapter.WorkoutAdapter
import com.example.projecttest.screen.courses.CourseDetail
import com.example.projecttest.viewmodel.CourseViewModel
import com.example.projecttest.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //    private lateinit var mucDoAdapter: RvAdapterMucDo
    private lateinit var thuThachAdapter: RvAdapterThuThach
    private lateinit var workoutAdapter: WorkoutAdapter
    private val userSummaryViewModel: UserSummaryViewModel by viewModels()
    private lateinit var courseViewModel: CourseViewModel

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java) // gọi viewModel

        lifecycleScope.launch {
            try {
                courseViewModel.fetchDataListHome() // lấy dữ liệu

                courseViewModel.trainingProgramListHome.collect {

                    createItemListHome(it) /// hàm sửa lí việc in danh sách ra
                }


            } catch (e: Exception) {
                // Xử lý lỗi ở đây
                println("Error: ${e.message}")
            }
        }
        // Truyền sự kiện click vào Adapter
        // Lấy userId từ Firebase Authentication
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Kiểm tra nếu userId không null
        if (userId != null) {
            // Khi vào màn hình, tự động fetch dữ liệu
            userSummaryViewModel.fetchUserSummary()
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
        thuThachAdapter = RvAdapterThuThach(emptyList()) { thuThachItem ->

            Toast.makeText(requireContext(), "${thuThachItem.name}", Toast.LENGTH_SHORT).show()


            val intent = Intent(requireContext(), CourseDetail::class.java)
            startActivity(intent)
        }

        binding.rvWorkPrograms.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = thuThachAdapter
        }
    }

    private fun createItemListHome(ds: List<Course>) {
        val list = createListWorkOut(ds)// tạo ds cho list bài tập
        setAdapter(list)

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

    private fun createListWorkOut(list: List<Course>): MutableList<Course> {
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

    private fun setAdapter(ds: List<Course>) {
        val adapter = WorkoutAdapter(ds, object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            val selectedCourse = ds[position]
            Toast.makeText(requireContext(), "${selectedCourse.name}", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), CourseDetail::class.java)
            intent.putParcelableArrayListExtra("Courses", ArrayList(selectedCourse.modules))
            intent.putExtra("nameCourseDetail", selectedCourse.name)
            intent.putExtra("level", selectedCourse.level)
//            intent.putExtra("img", selectedCourse.img)
            intent.putExtra("totalTime", selectedCourse.totalTime)
            startActivity(intent)
        }
    })

        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }
    private fun observeHomeList() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.homeList.collect { homeList ->
                homeList?.let {
                    thuThachAdapter.updateList(it.flatMap { home -> home.thuThach })
                }
            }
        }
    }
}