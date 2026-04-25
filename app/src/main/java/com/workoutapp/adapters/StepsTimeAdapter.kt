package com.workoutapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R

class StepsTimeAdapter(
    private val items: List<String>,
    private val onItemClick: (Int, String) -> Unit
) : RecyclerView.Adapter<StepsTimeAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn: Button = itemView.findViewById(R.id.btnStepsTimeFrame)

        fun bind(text: String, position: Int) {
            btn.text = text

            // ✔ SINGLE click listener (ONLY HERE)
            btn.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION) return@setOnClickListener

                println("clicked pos = $pos")

                val old = selectedPosition
                selectedPosition = pos

                notifyItemChanged(old)
                notifyItemChanged(pos)

                onItemClick(pos, items[pos])
            }

            val isSelected = position == selectedPosition

            println("bind pos=$position selected=$isSelected")

            btn.isSelected = isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_steps_time_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size
}