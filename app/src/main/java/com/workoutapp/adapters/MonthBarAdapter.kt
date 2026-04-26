package com.workoutapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R

class MonthBarAdapter(val data: List<Int>) : RecyclerView.Adapter<MonthBarAdapter.ViewHolder>() {

    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_month_bar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.label.text = months[position]

        val value = data[position]
        val max = 100

        val height = (value / max.toFloat() * 120).toInt()

        val params = holder.bar.layoutParams
        params.height = height
        holder.bar.layoutParams = params
    }

    override fun getItemCount() = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bar: View = view.findViewById(R.id.barValue)
        val label: TextView = view.findViewById(R.id.monthLabel)
    }
}