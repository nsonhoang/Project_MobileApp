package com.example.projecttest.screen.food


import androidx.appcompat.app.AppCompatActivity


class Food : AppCompatActivity() {
//    private lateinit var binding: ActivityFoodBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityFoodBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        addEvents()
//    }
//
//    private fun addEvents() {
//        setListFood()
//        setTitleTypeFood()
//        setImageTypeFood()
//        setOnClickBack()
//    }
//
//    private fun setOnClickBack() {
//        binding.btnExit.setOnClickListener{
//            finish()
//        }
//    }
//
//    private fun setImageTypeFood() {
//
//    }
//
//    private fun setTitleTypeFood() {
//
//    }
//
//    private fun setListFood() {
//       val ds = createListFood()
//        setAdapter(ds)
//    }
//
//    private fun setAdapter(ds : List<OutData>) {
//        val adapter = RvAdapterDetailCourse(ds, object : OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                Toast.makeText(this@Food, "Item $position", Toast.LENGTH_SHORT).show()
//            }
//        })
//        binding.LvFood.adapter=adapter
//        binding.LvFood.layoutManager= GridLayoutManager(this,1, GridLayoutManager.VERTICAL,false)
//
//
//    }
//
//    private fun createListFood(): MutableList<OutData> {
//        val ds = mutableListOf<OutData>()
//        return ds
//    }
}