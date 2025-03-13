package com.example.projecttest.screen.course

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projecttest.R
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.ActivityCourseBinding
import com.example.projecttest.screen.adapter.LvAdapterCourse

class Course : AppCompatActivity() {
    private lateinit var binding: ActivityCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addEvent()
    }

    private fun addEvent() {
        val ds =createListCourse()
        setAdapterCourse(ds)
        setTextForNameCourse() // đặt text cho mục mình nhấn vào mặc định là "SÁU MÚI"
        setTextForTitleCourse() // đặt text title cho mục mình nhấn vào mặc định là "..."
    }

    private fun setTextForTitleCourse() {

    }

    private fun setTextForNameCourse() {

    }

    private fun setAdapterCourse(ds :List<OutData>) {
        val adapter = LvAdapterCourse(ds)
        binding.LvCourse.adapter=adapter
        binding.LvCourse.layoutManager= GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)

    }

    private fun createListCourse():MutableList<OutData> {
        val ds  = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))


        return ds

    }

}