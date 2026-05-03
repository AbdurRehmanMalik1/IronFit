package com.workoutapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.models.TaskUI


class CalendarTaskAdapter(
    private var list: List<TaskUI>
) : RecyclerView.Adapter<CalendarTaskAdapter.ViewHolder>() {

    fun submitList(newList: List<TaskUI>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvWorkoutTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_suggest_workout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.i("CalendarAdapter", "Binding item at position: $position")

        holder.title.text = list[position].title
    }

    override fun getItemCount() = list.size
}