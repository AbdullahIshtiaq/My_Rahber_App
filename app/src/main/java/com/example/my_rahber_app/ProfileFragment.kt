package com.example.my_rahber_app

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_rahber_app.Constant.Companion.STUDENT_ID
import com.example.my_rahber_app.database.AppDatabase
import com.example.my_rahber_app.database.Course
import com.example.my_rahber_app.database.DatabaseProvider
import com.example.my_rahber_app.database.Student
import com.example.my_rahber_app.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: AppDatabase
    private var student: Student? = null

    private lateinit var textAdapter: SingleTextAdapter

    private lateinit var sharedPreferences: SharedPreferences


    private var studentId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        database = DatabaseProvider.provideDatabase(requireActivity())
        sharedPreferences =
            requireActivity().getSharedPreferences(Constant.MY_PREFERENCES, MODE_PRIVATE)

        textAdapter = SingleTextAdapter()
        binding.enrolledCoursesProfile.adapter = textAdapter
        binding.enrolledCoursesProfile.layoutManager = LinearLayoutManager(requireActivity())

        studentId = sharedPreferences.getInt(STUDENT_ID, 0)

        setStudent()

        binding.studentEnrolledCard.setOnClickListener {
            onEnrollClicked()
        }
        return binding.root
    }

    private fun setStudent() {
        CoroutineScope(Dispatchers.IO).launch {
            student = database.studentDao().getStudentById(studentId)
            val enrolledCourseCode = student?.enrolledCourses?.split(",")
            val enrolledCourse: List<Course> = enrolledCourseCode?.map {
                database.studentDao().getCourseByCode(it.toInt())
            } ?: emptyList()
            withContext(Dispatchers.Main) {
                binding.apply {
                    studentNameTextview.text = student?.name
                    studentEmailTextview.text = student?.email

                    if (enrolledCourseCode?.size == 1 && enrolledCourseCode.first() == "") {
                        enrolledCoursesNumber.text = "0"
                    } else {
                        enrolledCoursesNumber.text = enrolledCourseCode?.size.toString()
                        textAdapter.setCourses(enrolledCourse.map { it.title })
                    }
                }
            }
        }
    }

    private fun onEnrollClicked() {
        binding.apply {
            if (enrolledCoursesProfile.visibility == View.VISIBLE) {
                enrolledCoursesProfile.gone()
            } else {
                enrolledCoursesProfile.show()
            }
        }
    }

}