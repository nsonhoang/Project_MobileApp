package com.example.projecttest.screen.discoverfragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.example.projecttest.screen.adapter.RvAdapter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecttest.R
import com.example.projecttest.data.Course
import com.example.projecttest.data.OutData
import com.example.projecttest.data.TargetCourse
import com.example.projecttest.databinding.FragmentDiscoverBinding
import com.example.projecttest.viewmodel.CourseViewModel
import com.example.projecttest.screen.adapter.LvAdapter
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.adapter.RvAdapter2
import com.example.projecttest.screen.adapter.RvAdapter3
import com.example.projecttest.screen.courses.CourseDetail
import com.example.projecttest.screen.courses.Courses
import com.example.projecttest.screen.food.Food
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class Discover : Fragment() {

    private lateinit var courseViewModel: CourseViewModel

    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding = FragmentDiscoverBinding.inflate(inflater, container, false)

        binding.progressBar.visibility= View.VISIBLE
        if (binding.progressBar.isVisible){
            binding.scrollDiscover.visibility = View.GONE

        }

        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)



        lifecycleScope.launch {
            try {

                courseViewModel.fetchDataTrainingProgram()

                courseViewModel.fetchDataListForYouAndStretching()
                // Thu thập dữ liệu từ trainingProgramListForYou
                delay(2000)
                launch {
                    courseViewModel.trainingProgramListForYou.collectLatest {
                        createItemRecycler2(it)
                        setOnClickBtnCourseToday(it)
                    }
                }

                // Thu thập dữ liệu từ trainingProgramListStretching
                launch {
                    courseViewModel.trainingProgramListStretching.collectLatest {
                        createItemRecyclerStretching(it) //mục giãn cơ

                    }
                }

                // Thu thập dữ liệu từ trainingProgram
                launch {
                    courseViewModel.trainingProgram.collectLatest { courseList ->
                        courseList.forEach { course ->
                            createItemRecycler(course.targetCourses)
                        }
                    }
                }
            } catch (e: Exception) {
                // Xử lý lỗi ở đây
                println("Error: ${e.message}")

            }
            finally {
                binding.progressBar.visibility = View.GONE
                binding.scrollDiscover.visibility = View.VISIBLE
            }
        }


//        createItemRecycler() // mục đầu tiên
//        createItemRecycler2() // mục dành cho bạn


        createListView()//dem kalo
        return binding.root
    }
    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Lỗi")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false) // Không cho phép đóng bằng cách nhấn ra ngoài
        val dialog = builder.create()
        dialog.show()
    }

    private fun setOnClickBtnCourseToday(list: List<Course>) {
        binding.btnCourseToday.setOnClickListener {
            val intent =Intent(requireContext(),CourseDetail::class.java)
            intent.putParcelableArrayListExtra("Courses", ArrayList(list.first().modules))//truyền mảng này qua để hiển thị danh sách các bài tập
            intent.putExtra("nameCourseDetail", binding.txtCourseToday.text.toString())
            intent.putExtra("level",list.first().level)
            intent.putExtra("totalTime",list.first().totalTime)

//                intent.putExtra("img",)
            startActivity(intent)


        }
    }

    private fun createItemRecycler(list: List<TargetCourse>){
        val ds = createOutDataList(list)
        ds.reversed()
        setAdapter(ds) // trả về layout ở mục đầu tiên
    }


    private fun createItemRecycler2(list: List<Course>){
        val listForYou = createOutDataListForYou(list)
        setAdapterForYou(listForYou )//trả về layout ở mục dành cho bạn
    }

    private  fun createItemRecyclerStretching(list: List<Course>){
        val list = createOutDataListStretching(list)

        setAdapterStretching(list) // tạo layout ở mục khởi đông
    }


    private fun  createOutDataList(list: List<TargetCourse>) : MutableList<TargetCourse>{
        val ds = mutableListOf<TargetCourse>()

        list.forEach {
            it->
            ds.add(TargetCourse(it.img,it.name,it.detail,it.courses))
        }

//        ds.add(OutData(R.drawable.saumuireal, "Sáu múi", "13 bài"))
//        ds.add(OutData(R.drawable.arm, "Cánh tay & vai", "13 bài"))
//        ds.add(OutData(R.drawable.nguc, "Sáu múi", "13 bài"))
//        ds.add(OutData(R.drawable.leg, "Mông & Chân", "13 bài"))

        return ds
    }

    private fun setAdapter(ds: List<TargetCourse>){
        val adapter = RvAdapter(ds, object : OnItemClickListener{
            override fun onItemClick(position: Int) {

                val i = Intent(requireActivity(), Courses::class.java)
                i.putExtra("nameCourse", ds[position].name)
                i.putExtra("img",ds[position].img)
                i.putExtra("detailCourse",ds[position].detail)

                i.putParcelableArrayListExtra("program", ArrayList(ds[position].courses))
                startActivity(i)
            }
        })
        binding.recyclerContainor.adapter = adapter // Set adapter cho RecyclerView
        binding.recyclerContainor.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false) // Sử dụng LinearLayoutManager với hướng ngang


    }

    private fun setAdapterForYou(ds: List<Course>){
        val adapter = RvAdapter2(ds,object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), ds[position].name, Toast.LENGTH_SHORT).show()


                val intent =Intent(requireContext(),CourseDetail::class.java)
                intent.putParcelableArrayListExtra("Courses", ArrayList(ds[position].modules))//truyền mảng này qua để hiển thị danh sách các bài tập
                intent.putExtra("nameCourseDetail", ds[position].name)
                intent.putExtra("level",ds[position].level)
                intent.putExtra("totalTime",ds[position].totalTime)
//                intent.putExtra("img",)
                startActivity(intent)
            }

        })
        binding.recyclerForYou.adapter = adapter // Set adapter cho RecyclerView
        binding.recyclerForYou.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false) // Sử dụng LinearLayoutManager với hướng ngang
    }

    private fun createOutDataListForYou(list: List<Course>) :MutableList<Course>{
        val ds = mutableListOf<Course>()
        list.forEach { course ->
            ds.add(Course(course.name, course.detail, course.level, course.totalTime, course.img, course.modules))
        }

        return ds
    }

    private fun setAdapterStretching(ds: List<Course>){
        val adapter = RvAdapter3(ds,object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), ds[position].name, Toast.LENGTH_SHORT).show()


                val intent =Intent(requireContext(),CourseDetail::class.java)
                intent.putParcelableArrayListExtra("Courses", ArrayList(ds[position].modules))//truyền mảng này qua để hiển thị danh sách các bài tập
                intent.putExtra("nameCourseDetail", ds[position].name)
                intent.putExtra("level",ds[position].level)
                intent.putExtra("totalTime",ds[position].totalTime)
//                intent.putExtra("img",)
                startActivity(intent)
            }

        })
        binding.recyclerStretching.adapter =adapter
        binding.recyclerStretching.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
    }

    private fun createOutDataListStretching(list: List<Course>):MutableList<Course>{
        val ds = mutableListOf<Course>()

        list.forEach { course ->
            ds.add(Course(course.name, course.detail, course.level, course.totalTime, course.img, course.modules))
        }



//        ds.add(OutData(R.drawable.keodantoanthan,"Kéo dãn toàn thân",""))
//        ds.add(OutData(R.drawable.giamcangvai,"Giảm căng vai",""))
//        ds.add(OutData(R.drawable.khoidongbuoisang,"Khởi động buổi sáng",""))
//        ds.add(OutData(R.drawable.ngungon,"Kéo dài thời gian ngủ",""))
//        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
//        ds.add(OutData(R.drawable.giamdaulung,"Giảm đâu lưng",""))
//        ds.add(OutData(R.drawable.giamdaudaugoi,"Giảm đau đầu gối",""))

        return ds
    }

    private fun createListView(){
        val ds = createOutDataListview()
        setAdapterListView(ds)
    }

    private fun setAdapterListView(ds: List<OutData>) {
        val adapter = LvAdapter(ds,object :OnItemClickListener{
            override fun onItemClick(position: Int) {
                val i = Intent(requireActivity(),Food::class.java)

                startActivity(i)
            }
        })
        binding.lvCalo.adapter = adapter
        binding.lvCalo.layoutManager = GridLayoutManager(requireContext(),1,GridLayoutManager.VERTICAL,false)
    }

    private fun createOutDataListview(): MutableList<OutData> {
        val ds = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.tinhboot,"Thực phẩm tinh bột","Vd: gạo, bánh mì..."))
        ds.add(OutData(R.drawable.thit,"Thực phẩm Protein động vật","Vd: thịt heo, thit bò..."))
        ds.add(OutData(R.drawable.proteinthucvat,"Thực phẩm Protein thực vật","Vd: các loại đậu..."))
        ds.add(OutData(R.drawable.raucuqua,"Thực phẩm từ rau củ quả","Vd: rau cải, cam..."))
        ds.add(OutData(R.drawable.douong,"Thực phẩm từ đồ uống ","Vd: bia, đồ uống có gas..."))

        return ds
    }
}