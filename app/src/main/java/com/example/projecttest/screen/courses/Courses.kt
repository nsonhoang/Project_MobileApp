package com.example.projecttest.screen.courses

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecttest.R
import com.example.projecttest.data.OutData


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
        addEvents()
    }

    private fun addEvents() {
        setListCourse()
        setTextForNameCourse() // đặt text cho mục mình nhấn vào mặc định là "SÁU MÚI"
        setTextForTitleCourse() // đặt text title cho mục mình nhấn vào mặc định là "..."
        setEventClickBack()
    }

    private fun setListCourse() {
        val ds =createListCourse()
        setAdapterCourse(ds)
    }

    private fun setEventClickBack() {
        binding.btnExit.setOnClickListener({
            finish() // Kết thúc Activity hiện tại
        })
    }

    private fun setTextForTitleCourse() {

    }

    private fun setTextForNameCourse() {

    }

    private fun setAdapterCourse(ds :List<OutData>) {
        val adapter = LvAdapterCourse(ds, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@Courses, "Item $position", Toast.LENGTH_SHORT).show()
                val intent =Intent(this@Courses,CourseDetail::class.java)
                startActivity(intent)
            }
        })
        binding.LvCourse.adapter=adapter
        binding.LvCourse.layoutManager= GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)


    }

    private fun createListCourse():MutableList<OutData> {
        val ds  = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.fire,"Đốt cháy mỡ bụng với HIIT người mới bắt đầu","14 phút + Người bắt đầu"))


        return ds

    }


}