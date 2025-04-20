package com.example.projecttest.screen.courses

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.projecttest.R
import com.example.projecttest.data.CourseModule
import com.example.projecttest.databinding.ActivityCourseDetailBinding
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.ReadyActivity
import com.example.projecttest.screen.adapter.RvAdapterDetailCourse

class CourseDetail : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val receivedModule = intent.getParcelableArrayListExtra<CourseModule>("Courses")
        val nameCourse = intent.getStringExtra("nameCourseDetail") ?: ""
        val level = intent.getStringExtra("level")  ?: ""
        val img = intent.getStringExtra("img")  ?: ""
        val totalTime = intent.getIntExtra("totalTime",0)



        receivedModule?.let {
            addEvends(receivedModule,nameCourse,level,img,totalTime)
        }


    }

    private fun addEvends(
        list: List<CourseModule>,
        name: String,
        level: String,
        img: String,
        totalTime: Int
    ) {
        setNameCourse(name)  // cài tên bài tập mình click vào
        setImgCourse(img) // cài hình ảnh ở mục mình click vào
        setInforCourse(level,totalTime,list) // cài đặt thông tin như cấp độ tập, thời gian, số bài tập
        setListCourseDetail(list) // cài đặt hiện thị danh sách các bài tập
        setEventOnClickStart() /// cài đặt sự kiện khi mình click vào bắt đầu
        setEventOnclickBackArrow()
    }

    private fun setEventOnclickBackArrow() {
        binding.btnExit.setOnClickListener {
            finish()
        }
    }

    private fun setEventOnClickStart() {
        binding.button.setOnClickListener {
            // Chuyển đến màn hình Ready
            val intent = Intent(this, ReadyActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setListCourseDetail(list: List<CourseModule>) {
        val list = createListCourseDetail(list)
        setAdapter(list)
    }

    private fun setAdapter( ds: List<CourseModule>) {
        val adapter = RvAdapterDetailCourse(ds,object : OnItemClickListener{
            override fun onItemClick(position: Int) {

            }
        })
        binding.LvCourseDetail.adapter=adapter
        binding.LvCourseDetail.layoutManager= GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)


    }

    private fun createListCourseDetail(list: List<CourseModule>): MutableList<CourseModule> {
        val ds = mutableListOf<CourseModule>()

        list.forEach {
            it->
            ds.add(CourseModule(it.name,it.trainingTime,it.img))
        }

        return ds
    }

    private fun setInforCourse(level: String, totalTime: Int, list: List<CourseModule>) {
        binding.txtLevel.setText(level)
        binding.txtTime.setText(totalTime.toString() + " Phút")
        binding.txtTotal.setText(list.size.toString())

    }

    private fun setImgCourse(img: String) {
        Glide.with(this)
            .load(img)
            .into(binding.img)

    }

    private fun setNameCourse(name: String) {
        binding.txtNameCousse.setText(name)

    }
}