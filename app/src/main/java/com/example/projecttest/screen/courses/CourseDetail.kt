package com.example.projecttest.screen.courses

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecttest.R
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.ActivityCourseDetailBinding
import com.example.projecttest.screen.adapter.LvAdapterCourse
import com.example.projecttest.screen.adapter.OnItemClickListener

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

        addEvends()
    }

    private fun addEvends() {
        setNameCourse()  // cài tên bài tập mình click vào
        setImgCourse() // cài hình ảnh ở mục mình click vào
        setInforCourse() // cài đặt thông tin như cấp độ tập, thời gian, số bài tập
        setListCourseDetail() // cài đặt hiện thị danh sách các bài tập
        setEventOnClickStart() /// cài đặt sự kiện khi mình click vào bắt đầu

    }

    private fun setEventOnClickStart() {

    }

    private fun setListCourseDetail() {
        val list = createListCourseDetail()
        setAdapter(list)
    }

    private fun setAdapter( ds: List<OutData>) {
        val adapter = LvAdapterCourse(ds,object : OnItemClickListener{
            override fun onItemClick(position: Int) {

            }
        })
        binding.LvCourseDetail.adapter=adapter
        binding.LvCourseDetail.layoutManager= GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)


    }

    private fun createListCourseDetail(): MutableList<OutData> {
        val ds = mutableListOf<OutData>()

//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))
//        ds.add(OutData(R.drawable.buttkicks,"Đá mông","00:30"))


        return ds
    }

    private fun setInforCourse() {

    }

    private fun setImgCourse() {

    }

    private fun setNameCourse() {

    }
}