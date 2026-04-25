package com.workoutapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.models.MetricUI
import androidx.core.graphics.toColorInt

class MetricsAdapter(private val metricUIS: List<MetricUI>) :
    RecyclerView.Adapter<MetricsAdapter.MetricViewHolder>() {

    class MetricViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvMetricTitle)
        val tvValue: TextView = view.findViewById(R.id.tvMetricValue)
        val tvUnit: TextView = view.findViewById(R.id.tvMetricUnit)
        val ivIcon: ImageView = view.findViewById(R.id.ivMetricIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetricViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_metrics_item, parent, false)
        return MetricViewHolder(view)
    }

    override fun onBindViewHolder(holder: MetricViewHolder, position: Int) {
        val metric = metricUIS[position]
        holder.tvTitle.text = metric.title
        holder.tvValue.text = metric.value
        holder.tvUnit.text = metric.unit

        holder.ivIcon.setImageResource(metric.iconResId)
        holder.ivIcon.setColorFilter(metric.colorHex.toColorInt())
    }

    override fun getItemCount() = metricUIS.size
}