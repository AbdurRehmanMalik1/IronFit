package com.workoutapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.adapters.CalendarTaskAdapter
import com.workoutapp.models.TaskUI
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var rvCalendarWorkouts: RecyclerView
    private lateinit var adapter: CalendarTaskAdapter
    private lateinit var etTaskTitle: EditText
    private lateinit var btnAddTask: ImageView

    private var selectedDate: String = ""

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById(R.id.calendarView)
        rvCalendarWorkouts = view.findViewById(R.id.rvCalendarWorkouts)
        etTaskTitle = view.findViewById(R.id.etTaskTitle)
        btnAddTask = view.findViewById(R.id.btnAddTask)

        adapter = CalendarTaskAdapter(listOf(
            TaskUI("Running"),
            TaskUI("Walking")
        ))
        rvCalendarWorkouts.adapter = adapter

        selectedDate = getToday()


//        // DATE CHANGE
//        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            selectedDate = formatDate(year, month, dayOfMonth)
//            loadTasks(selectedDate)
//        }

        // ADD TASK
        btnAddTask.setOnClickListener {
            val title = etTaskTitle.text.toString().trim()
            if (title.isEmpty()) return@setOnClickListener

            createTask(title)
        }
    }


    private fun createTask(title: String) {

//        val task = Task(
//            title = title,
//            date = selectedDate
//        )

//        ApiClient.service.createTask(task)
//            .enqueue(object : Callback<Task> {
//
//                override fun onResponse(call: Call<Task>, response: Response<Task>) {
//                    etTaskTitle.text.clear()
//                    loadTasks(selectedDate)
//                }
//
//                override fun onFailure(call: Call<Task>, t: Throwable) {}
//            })
    }
    private fun getToday(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
    }
}