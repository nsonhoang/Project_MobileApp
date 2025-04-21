// file: com.example.projecttest.screen.adapter/RvAdapterThuThachFirestore.kt

package com.example.projecttest.screen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecttest.data.thuThach
import com.example.projecttest.R

class RvAdapterThuThach(
    private var listThuThach: List<thuThach>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RvAdapterThuThach.ThuThachViewHolder>() {

    // ViewHolder sử dụng findViewById để ánh xạ các view
    inner class ThuThachViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivThumbnail: ImageView = view.findViewById(R.id.ivThumbnail)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun getItemCount(): Int {
        return listThuThach.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThuThachViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_program, parent, false)
        return ThuThachViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThuThachViewHolder, position: Int) {
        val item = listThuThach[position]

        // Thiết lập tên
        holder.tvTitle.text = item.name

        // Tải ảnh từ URL vào ImageView bằng Glide
        Glide.with(holder.itemView.context)
            .load(item.img)
            .into(holder.ivThumbnail)


        

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }


    // Hàm để cập nhật dữ liệu khi fetch Firestore mới
    fun updateList(newList: List<thuThach>) {
        listThuThach = newList
        notifyDataSetChanged()
    }
}
