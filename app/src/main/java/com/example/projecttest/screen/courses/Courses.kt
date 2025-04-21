package com.example.projecttest.screen.courses

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.projecttest.R
import com.example.projecttest.data.Course
import com.example.projecttest.databinding.ActivityCoursesBinding
import com.example.projecttest.screen.adapter.LvAdapterCourse
import com.example.projecttest.screen.adapter.OnItemClickListener

class Courses : AppCompatActivity() {
    private lateinit var binding: ActivityCoursesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val receivedCourses = intent.getParcelableArrayListExtra<Course>("program")

        val nameCourse = intent.getStringExtra("nameCourse") ?: ""
        val img= intent.getStringExtra("img")  ?: ""
        val detail = intent.getStringExtra("detailCourse") ?: ""
        println(receivedCourses)


        receivedCourses?.let{
            addEvents(it,nameCourse,img,detail)
        }

    }

    private fun addEvents(ds: List<Course>,nameCourse: String,img: String,detailCourse : String) {
        setListCourse(ds,img)
        setTextForNameCourse(nameCourse) // đặt text cho mục mình nhấn vào mặc định là "SÁU MÚI"
        setTextForTitleCourse(detailCourse) // đặt text title cho mục mình nhấn vào mặc định là "..."
        setEventClickBack()
        setImage(img)
    }

    private fun setImage(img: String) {
        Glide.with(this)
            .load(img)
            .into(binding.img)

    }

    private fun setListCourse(ds: List<Course>, img: String) {
        val ds =createListCourse(ds)
        setAdapterCourse(ds,img)
    }

    private fun setEventClickBack() {
        binding.btnExit.setOnClickListener({
            finish() // Kết thúc Activity hiện tại
        })
    }

    private fun setTextForTitleCourse(detail : String) {
        binding.txtTitleCourse.text=detail
    }

    private fun setTextForNameCourse(name: String) {
        binding.txtNameCousse.setText(name)

    }

    private fun setAdapterCourse(ds: List<Course>, img: String) {
        val adapter = LvAdapterCourse(ds, object : OnItemClickListener {
            override fun onItemClick(position: Int) {

                Toast.makeText(this@Courses, ds[position].name, Toast.LENGTH_SHORT).show()


                val intent =Intent(this@Courses,CourseDetail::class.java)
                intent.putParcelableArrayListExtra("Courses", ArrayList(ds[position].modules))//truyền mảng này qua để hiển thị danh sách các bài tập
                intent.putExtra("nameCourseDetail", ds[position].name)
                intent.putExtra("level",ds[position].level)
                intent.putExtra("totalTime",ds[position].totalTime)
                intent.putExtra("img",img)
                startActivity(intent)
            }
        })
        binding.LvCourse.adapter=adapter
        binding.LvCourse.layoutManager= GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)


    }

    private fun createListCourse(list: List<Course>):MutableList<Course> {
        val ds  = mutableListOf<Course>()

        list.forEach {
            it->
            ds.add(Course(it.name,it.detail,it.level,it.totalTime,it.img,it.modules))
        }
//        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))


        return ds

    }


}