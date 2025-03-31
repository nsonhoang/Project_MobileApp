package com.example.projecttest.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.Data.KieuBaiTap
import com.example.projecttest.R

class WorkoutAdapter(private val workouts: List<KieuBaiTap>):
        RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgbaitap : ImageView= view.findViewById(R.id.imgWorkout)
        val txtTitle : TextView= view.findViewById(R.id.txtTitleType)
        val txtTime : TextView= view.findViewById(R.id.tvExerciseTime)
        val txtCount : TextView= view.findViewById(R.id.tvExerciseCount)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout= workouts[position]
        holder.txtTitle.text= workout.tenkhoatap
        holder.imgbaitap.setImageResource(workout.imgbaitap)
        holder.txtTime.text=workout.thoigian
        holder.txtCount.text= workout.baitap
    }

    override fun getItemCount(): Int = workouts.size
}
