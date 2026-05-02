package com.workoutapp.activities

import MetricsViewModel
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.adapters.MetricSelectAdapter


class MetricsActivity : AppCompatActivity() {

    private lateinit var adapter: MetricSelectAdapter
    private val metricsViewModel: MetricsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrics)

        val rv = findViewById<RecyclerView>(R.id.rvAllMetrics)
        val btnSave = findViewById<Button>(R.id.btnSaveMetrics)

        rv.layoutManager = LinearLayoutManager(this)

        adapter = MetricSelectAdapter(mutableListOf())
        rv.adapter = adapter

        // LOAD
        metricsViewModel.loadMetricSelection()

        // OBSERVE (CORRECT LIVE DATA)
        metricsViewModel.selectableMetrics.observe(this) { items ->
            println("items data updated")
            adapter.updateSelectedMetricsData(items)
        }


        // SAVE
        btnSave.setOnClickListener {
            val selected = adapter.getSelected().map { it.type }
            metricsViewModel.saveSubscriptions(selected)
            finish()
        }
    }
}
