// file: com.example.projecttest.screen.adapter/RvAdapterMucDo.kt

package com.example.projecttest.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecttest.R
import com.example.projecttest.data.mucDo

class RvAdapterMucDo(
    private var listMucDo: List<mucDo>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RvAdapterMucDo.MucDoViewHolder>() {

    inner class MucDoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgWorkout: ImageView = view.findViewById(R.id.imgWorkout)
        val txtTitleType: TextView = view.findViewById(R.id.txtTitleType)
        val tvExerciseTime: TextView = view.findViewById(R.id.tvExerciseTime)
        val tvExerciseCount: TextView = view.findViewById(R.id.tvExerciseCount)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MucDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return MucDoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMucDo.size
    }

    override fun onBindViewHolder(holder: MucDoViewHolder, position: Int) {
        val item = listMucDo[position]

        // Load ảnh bằng Glide
        Glide.with(holder.itemView.context)
            .load(item.img)
            .into(holder.imgWorkout)

        // Set dữ liệu tên
        holder.txtTitleType.text = item.name

        // Set thời gian tập
        holder.tvExerciseTime.text = "Thời gian: ${item.timeTraining} phút"

        // Set chi tiết (ví dụ: "20 phút + 7 bài tập")
        holder.tvExerciseCount.text = item.detail

        // Bắt sự kiện click
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }


    fun updateList(newList: List<mucDo>) {
        listMucDo = newList
        notifyDataSetChanged()
    }

}
