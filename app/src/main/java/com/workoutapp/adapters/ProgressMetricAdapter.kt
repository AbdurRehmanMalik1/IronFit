package com.workoutapp.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.models.ProgressMetricUI

class ProgressMetricAdapter(
    private val metrics: List<ProgressMetricUI>
) : RecyclerView.Adapter<ProgressMetricAdapter.ProgressMetricViewHolder>() {

    class ProgressMetricViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvProgressMetricTitle)
        val tvValue: TextView = view.findViewById(R.id.tvProgressMetricValue)
        val tvUnit: TextView = view.findViewById(R.id.tvProgressMetricUnit)
        val ivIcon: ImageView = view.findViewById(R.id.imgProgressIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressMetricViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_progress_metric_item, parent, false)
        return ProgressMetricViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgressMetricViewHolder, position: Int) {
        val item = metrics[position]

        holder.tvTitle.text = item.title
        holder.tvValue.text = item.value
        holder.tvUnit.text = item.unit

        // Set icon
        holder.ivIcon.setImageResource(item.iconRes)

        // Apply tint color
        holder.ivIcon.setColorFilter(item.iconColor, PorterDuff.Mode.SRC_IN)
    }

    override fun getItemCount(): Int = metrics.size
}