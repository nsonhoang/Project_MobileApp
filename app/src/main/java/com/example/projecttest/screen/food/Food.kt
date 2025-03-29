package com.example.projecttest.screen.food


import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecttest.R
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.ActivityFoodBinding
import com.example.projecttest.screen.adapter.LvAdapterCourse
import com.example.projecttest.screen.adapter.OnItemClickListener


class Food : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addEvents()
    }

    private fun addEvents() {
        setListFood()
        setTitleTypeFood()
        setImageTypeFood()
        setOnClickBack()
    }

    private fun setOnClickBack() {
        binding.btnExit.setOnClickListener{
            finish()
        }
    }

    private fun setImageTypeFood() {

    }

    private fun setTitleTypeFood() {

    }

    private fun setListFood() {
       val ds = createListFood()
        setAdapter(ds)
    }

    private fun setAdapter(ds : List<OutData>) {
        val adapter = LvAdapterCourse(ds, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@Food, "Item $position", Toast.LENGTH_SHORT).show()
            }
        })
        binding.LvFood.adapter=adapter
        binding.LvFood.layoutManager= GridLayoutManager(this,1, GridLayoutManager.VERTICAL,false)


    }

    private fun createListFood(): MutableList<OutData> {
        val ds = mutableListOf<OutData>()
        return ds
    }
}