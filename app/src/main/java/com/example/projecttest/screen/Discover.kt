package com.example.projecttest.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projecttest.data.OutData
import com.example.projecttest.R
import com.example.projecttest.databinding.FragmentDiscoverBinding
import com.example.projecttest.screen.adapter.RvAdapter

class Discover : Fragment() {
    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)

        val ds = mutableListOf<OutData>()
        ds.add(OutData(R.drawable.saumuireal, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.arm, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.nguc, "Sáu múi", "13 bài"))
        ds.add(OutData(R.drawable.leg, "Sáu múi", "13 bài"))

        val adapter = RvAdapter(ds)
        binding.recyclerContainor.adapter = adapter // Set adapter cho RecyclerView
        binding.recyclerContainor.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false) // Sử dụng LinearLayoutManager với hướng ngang
        return binding.root
    }


}