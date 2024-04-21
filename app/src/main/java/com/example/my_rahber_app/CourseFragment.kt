package com.example.my_rahber_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_rahber_app.database.AppDatabase
import com.example.my_rahber_app.database.Course
import com.example.my_rahber_app.database.DatabaseProvider
import com.example.my_rahber_app.databinding.FragmentCourseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var courseAdapter: CourseAdapter

    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        database = DatabaseProvider.provideDatabase(requireActivity())

        binding = FragmentCourseBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {

        binding.apply {

            courseAdapter = CourseAdapter {
                requireActivity().navigateWithData<CourseDetailActivity>(it as Course)
            }
            recyclerView.adapter = courseAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val courses = getCourseList()
                    courseAdapter.setCourses(courses)
                }
            }
        }


    }

    private suspend fun getCourseList(): List<Course> {
        return withContext(Dispatchers.IO) {
            database.studentDao().getAllCourses()
        }
    }
}