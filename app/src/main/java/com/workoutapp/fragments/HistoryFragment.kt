package com.workoutapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.workoutapp.R
import com.workoutapp.adapters.StepsTimeAdapter

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvStepTime = view.findViewById<RecyclerView>(R.id.rvStepsTime);

        rvStepTime.layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_EVENLY
        }

        val stepsTimeAdapter = StepsTimeAdapter(
            listOf("1 Week", "2 Week", "3 Week", "1 Month")
        ) { position, value ->
        }

        rvStepTime.adapter = stepsTimeAdapter

    }
}