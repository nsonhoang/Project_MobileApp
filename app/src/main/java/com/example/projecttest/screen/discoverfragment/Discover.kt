package com.example.projecttest.screen.discoverfragment

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
import com.example.projecttest.screen.adapter.RvAdapter
import com.example.projecttest.screen.adapter.RvAdapter2
import com.example.projecttest.screen.adapter.RvAdapter3

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
        createListView()
        return binding.root
    }
    private fun createItemRecycler(){
        val ds = createOutDataList()
        setAdapter(ds) // trả về layout ở mục đầu tiên
    }
    private fun createItemRecycler2(){
        val listforyou = createOutDataListForYou()
        setAdapterForYou(listforyou )//trả về layout ở mục dành cho bạn

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
        val adapter = RvAdapter(ds)
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

        ds.add(OutData(R.drawable.gapbung, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.gapbung, "Cánh tay & vai", "13 bài"))
        ds.add(OutData(R.drawable.gapbung, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.gapbung, "Mông & Chân", "13 bài"))

        return ds
    }
    private fun setAdapterStretching(ds: List<OutData>){
        val adapter = RvAdapter3(ds)
        binding.recyclerStretching.adapter =adapter
        binding.recyclerStretching.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
    }

    private fun createOutDataListStretching():MutableList<OutData>{
        val ds = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))
        ds.add(OutData(R.drawable.covagiamcangvai,"Kéo dãn toàn thân",""))

        return ds
    }
    private fun createListView(){
        val ds = createOutDataListview()
        setAdapterListView(ds)
    }

    private fun setAdapterListView(ds: List<OutData>) {
        val adapter = LvAdapter(ds)
        binding.lvCalo.adapter = adapter
        binding.lvCalo.layoutManager = GridLayoutManager(requireContext(),1,GridLayoutManager.VERTICAL,false)
    }

    private fun createOutDataListview(): MutableList<OutData> {
        val ds = mutableListOf<OutData>()

        ds.add(OutData(R.drawable.tinhboot,"Thực phẩm tinh bột","vd: gạo, bánh mì..."))
        ds.add(OutData(R.drawable.tinhboot,"Thực phẩm tinh bột","vd: gạo, bánh mì..."))
        ds.add(OutData(R.drawable.tinhboot,"Thực phẩm tinh bột","vd: gạo, bánh mì..."))
        ds.add(OutData(R.drawable.tinhboot,"Thực phẩm tinh bột","vd: gạo, bánh mì..."))
        ds.add(OutData(R.drawable.tinhboot,"Thực phẩm tinh bột","vd: gạo, bánh mì..."))
        return ds
    }


}