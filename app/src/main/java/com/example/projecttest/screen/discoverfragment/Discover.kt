package com.example.projecttest.screen.discoverfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecttest.data.OutData
import com.example.projecttest.R
import com.example.projecttest.databinding.FragmentDiscoverBinding
import com.example.projecttest.screen.adapter.LvAdapter
import com.example.projecttest.screen.adapter.OnItemClickListener
import com.example.projecttest.screen.adapter.RvAdapter
import com.example.projecttest.screen.adapter.RvAdapter2
import com.example.projecttest.screen.adapter.RvAdapter3
import com.example.projecttest.screen.courses.CourseDetail
import com.example.projecttest.screen.courses.Courses
import com.example.projecttest.screen.food.Food

class Discover : Fragment() {
    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        createItemRecycler() // mục đầu tiên
        createItemRecycler2() // mục dành cho bạn
        createItemRecyclerStretching() //mục giãn cơ
        createListView()//dem kalo
        return binding.root
    }
    private fun createItemRecycler(){
        val ds = createOutDataList()
        setAdapter(ds) // trả về layout ở mục đầu tiên
    }


    private fun createItemRecycler2(){
        val listForYou = createOutDataListForYou()
        setAdapterForYou(listForYou )//trả về layout ở mục dành cho bạn

    }
    private  fun createItemRecyclerStretching(){
        val list = createOutDataListStretching()
        setAdapterStretching(list) // tạo layout ở mục khởi đông
    }


    private fun  createOutDataList() : MutableList<OutData>{
        val ds = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.saumuireal, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.arm, "Cánh tay & vai", "13 bài"))
        ds.add(OutData(R.drawable.nguc, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.leg, "Mông & Chân", "13 bài"))

        return ds
    }
    private fun setAdapter(ds: List<OutData>){
        val adapter = RvAdapter(ds,object :OnItemClickListener{
            override fun onItemClick(position: Int) {
                val selectedItem = ds[position] // Lấy item được nhấn
                val i = Intent(requireActivity(), Courses::class.java)
                startActivity(i)
            }
        })
        binding.recyclerContainor.adapter = adapter // Set adapter cho RecyclerView
        binding.recyclerContainor.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false) // Sử dụng LinearLayoutManager với hướng ngang

    }
    private fun setAdapterForYou(ds: List<OutData>){
        val adapter = RvAdapter2(ds)
        binding.recyclerForYou.adapter = adapter // Set adapter cho RecyclerView
        binding.recyclerForYou.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false) // Sử dụng LinearLayoutManager với hướng ngang
    }

    private fun createOutDataListForYou() :MutableList<OutData>{
        val ds = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.gapbung, "Đốt cháy mỡ bụng với HIIT", "14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.chongday, "Giảm mỡ (KHÔNG NHẢY)", "15 phút + Người trung bình"))
        ds.add(OutData(R.drawable.hittloaibongucxe, "HIIT loại bỏ ngực xệ", "13 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.hiit2, "Bài tập cốt lõi HIIT", "14 phút + Người bắt đầu"))
        ds.add(OutData(R.drawable.diamodpushup, "Làm rộng vai", "13 phút + Trung bình"))

        return ds
    }
    private fun setAdapterStretching(ds: List<OutData>){
        val adapter = RvAdapter3(ds)
        binding.recyclerStretching.adapter =adapter
        binding.recyclerStretching.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
    }

    private fun createOutDataListStretching():MutableList<OutData>{
        val ds = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.keodantoanthan,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.giamcangvai,"Giảm căng vai",""))
        ds.add(OutData(R.drawable.khoidongbuoisang,"Khởi động buổi sáng",""))
        ds.add(OutData(R.drawable.ngungon,"Kéo dài thời gian ngủ",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.giamdaulung,"Giảm đâu lưng",""))
        ds.add(OutData(R.drawable.giamdaudaugoi,"Giảm đau đầu gối",""))

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