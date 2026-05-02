package com.workoutapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.viewmodels.MetricSelectUI

class MetricSelectAdapter(
    private val items: MutableList<MetricSelectUI>
) : RecyclerView.Adapter<MetricSelectAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val unit: TextView = view.findViewById(R.id.tvUnit)
        val icon: ImageView = view.findViewById(R.id.ivIcon)
        val container: View = view.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_metric_select, parent, false)
        return VH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.title.text = item.title
        holder.unit.text = item.unit
        holder.icon.setImageResource(item.iconRes)

        // 🎨 APPLY COLOR
        val color = item.color.toColorInt()
        ImageViewCompat.setImageTintList(
            holder.icon,
            android.content.res.ColorStateList.valueOf(color)
        )

        holder.container.alpha = if (item.isSelected) 1f else 0.5f

        holder.container.setOnClickListener {
            item.isSelected = !item.isSelected
            notifyItemChanged(position)
        }
    }

    fun getSelected(): List<MetricSelectUI> {
        return items.filter { it.isSelected }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelectedMetricsData(newItems: List<MetricSelectUI>) {
        println("adding items : $newItems")
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}