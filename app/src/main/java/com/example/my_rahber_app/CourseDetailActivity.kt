package com.example.my_rahber_app

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.example.my_rahber_app.Constant.Companion.DATA
import com.example.my_rahber_app.Constant.Companion.ENROLLED
import com.example.my_rahber_app.Constant.Companion.START_LEARNING
import com.example.my_rahber_app.Constant.Companion.STUDENT_ID
import com.example.my_rahber_app.database.AppDatabase
import com.example.my_rahber_app.database.Course
import com.example.my_rahber_app.database.DatabaseProvider
import com.example.my_rahber_app.database.Student
import com.example.my_rahber_app.databinding.ActivityCourseDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailBinding
    private lateinit var database: AppDatabase

    private lateinit var sharedPreferences: SharedPreferences

    private var course: Course? = null
    private var studentId: Int = 0
    private var student: Student? = null


    private var isEnrolled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseProvider.provideDatabase(this)
        sharedPreferences = getSharedPreferences(Constant.MY_PREFERENCES, MODE_PRIVATE)

        binding.backBtnImageview.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        studentId = sharedPreferences.getInt(STUDENT_ID, 0)


        course = intent.getSerializable(DATA) as? Course

        setupUI()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {

        course?.let {
            binding.apply {
                courseTitleTextview.text = it.title
                courseDescriptionTextview.text = it.description
                courseInstructorTextview.text = it.instructor

                val video =
                    "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xT8oP0wy-A0\" title=\"Kotlin in 100 Seconds\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"

                webView.loadData(video, "text/html", "utf-8")
                webView.settings.javaScriptEnabled = true
                webView.webChromeClient = WebChromeClient()

            }
        }

        checkEnrolledStatus()

        binding.startLearningButton.setOnClickListener {
            setEnrolledStatus()
        }
    }

    private fun checkEnrolledStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            student = database.studentDao().getStudentById(studentId)
            val enrolledCourse = student?.enrolledCourses?.split(",") ?: emptyList()
            withContext(Dispatchers.Main) {
                if (enrolledCourse.size == 1 && enrolledCourse.first() == "") {
                    binding.startLearningButton.text = START_LEARNING
                    isEnrolled = false
                    return@withContext
                } else {
                    enrolledCourse.forEach {
                        if (it.toInt() == course!!.courseCode) {
                            binding.startLearningButton.text = ENROLLED
                            isEnrolled = true
                        }
                    }
                }
            }
        }
    }

    private fun setEnrolledStatus() {

        if (isEnrolled) {
            unEnrollStudentInCourse(course!!.courseCode)
            binding.startLearningButton.text = START_LEARNING
            isEnrolled = false
        } else {
            enrollStudentInCourse(course!!.courseCode)
            binding.startLearningButton.text = ENROLLED
            isEnrolled = true
        }

    }

    private fun enrollStudentInCourse(courseCode: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            student?.let {
                if (it.enrolledCourses.isEmpty()) {
                    it.enrolledCourses = courseCode.toString()
                } else {
                    it.enrolledCourses = it.enrolledCourses + ",$courseCode"
                }
                database.studentDao().updateStudent(it)
            }

        }
    }

    private fun unEnrollStudentInCourse(courseCode: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            student?.let {
                val courseCodeList = it.enrolledCourses.split(",").toMutableList()
                courseCodeList.remove(courseCode.toString())
                it.enrolledCourses = courseCodeList.joinToString(",")
                database.studentDao().updateStudent(it)
            }
        }
    }
}
