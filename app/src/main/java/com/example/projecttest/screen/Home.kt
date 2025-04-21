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
import com.example.projecttest.databinding.FragmentHomeBinding
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.adapter.RvAdapterMucDo
import com.example.projecttest.screen.adapter.RvAdapterThuThach
import com.example.projecttest.screen.courses.Courses
import com.example.projecttest.model.UserSummaryViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projecttest.data.Course
import com.example.projecttest.screen.adapter.WorkoutAdapter
import com.example.projecttest.viewmodel.CourseViewModel
import kotlinx.coroutines.launch
import com.example.projecttest.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class Home : Fragment(), OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mucDoAdapter: RvAdapterMucDo
    private lateinit var thuThachAdapter: RvAdapterThuThach

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


            }catch (e: Exception) {
                // Xử lý lỗi ở đây
                println("Error: ${e.message}")
            }
        }

//        val workoutPrograms = listOf(
//            WorkoutProgram("Toàn thân thử thách 7x4", 50, R.drawable.workout1),
//            WorkoutProgram("Thử thách cơ bụng", 30, R.drawable.workout2),
//        )

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
         val twAdapter = WorkoutAdapter(ds, this@Home)
        binding.rvWorkLevel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = twAdapter
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