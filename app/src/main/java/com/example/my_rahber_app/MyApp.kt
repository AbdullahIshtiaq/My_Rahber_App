package com.example.my_rahber_app

import android.app.Application
import android.content.SharedPreferences
import com.example.my_rahber_app.Constant.Companion.MY_PREFERENCES
import com.example.my_rahber_app.Constant.Companion.STUDENT_ID
import com.example.my_rahber_app.Constant.Companion.VISIT
import com.example.my_rahber_app.database.AppDatabase
import com.example.my_rahber_app.database.Course
import com.example.my_rahber_app.database.DatabaseProvider
import com.example.my_rahber_app.database.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApp : Application() {


    private lateinit var database: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        // Initialize the Room database
        database = DatabaseProvider.provideDatabase(this)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)

        val state = getIsFirstTime()

        if (!state) {
            setIsFirstTime()
            CoroutineScope(Dispatchers.IO).launch {
                database.studentDao()
                    .insertStudent(
                        Student(
                            name = "Jennifer Austin",
                            email = "student@uni.com",
                            enrolledCourses = ""
                        )
                    )

                val studentId = database.studentDao().getAllStudents().first().id
                setStudentId(studentId)
            }

            val courses = generateCourses()

            CoroutineScope(Dispatchers.IO).launch {
                courses.forEach {
                    database.studentDao().insertCourse(it)
                }
            }
        }
    }

    private fun setStudentId(studentId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(STUDENT_ID, studentId)
        editor.apply()
    }


    private fun setIsFirstTime() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(VISIT, true)
        editor.apply()
    }

    private fun getIsFirstTime(): Boolean {
        return sharedPreferences.getBoolean(VISIT, false)
    }

    private fun generateCourses(): List<Course> {
        return listOf(
            Course(
                courseCode = 101,
                title = "Advanced Kotlin Programming",
                instructor = "Dr. Jane Smith",
                description = " Dive deep into the world of Kotlin with this advanced course led by Dr. Jane Smith. Explore topics such as coroutines, functional programming, and Kotlin DSLs. This course is designed for experienced developers looking to enhance their Kotlin skills. You'll learn how to write efficient and robust applications using best practices.",
                imageUrl = "https://www.classcentral.com/report/wp-content/uploads/2022/08/BCG-Kotlint-Featured-Banner.png"
            ),
            Course(
                courseCode = 102,
                title = "Android App Development",
                instructor = "John Doe",
                description = "Learn how to create modern Android apps from scratch with expert instructor John Doe. This course covers the latest Android development tools and frameworks. You'll gain hands-on experience building apps with Kotlin and Jetpack components. By the end, you'll have the skills to create your own functional, user-friendly apps.",
                imageUrl = "https://www.classcentral.com/report/wp-content/uploads/2022/08/BCG-Kotlint-Featured-Banner.png"
            ),
            Course(
                courseCode = 103,
                title = "Machine Learning with Python",
                instructor = "Emily White",
                description = "Explore the world of machine learning with Python in this comprehensive course led by Emily White. Learn how to use libraries like TensorFlow and scikit-learn to create intelligent models. This course covers supervised and unsupervised learning, neural networks, and more. By the end, you'll have the knowledge to implement machine learning solutions in real-world projects.",
                imageUrl = "https://www.classcentral.com/report/wp-content/uploads/2022/08/BCG-Kotlint-Featured-Banner.png"
            ),
            Course(
                courseCode = 104,
                title = "Web Development with React",
                instructor = "Michael Green",
                description = " Discover the power of modern web development with React in this course by Michael Green. Learn how to build dynamic, responsive web applications using React and related tools. You'll work on hands-on projects and gain a deep understanding of state management and components. This course is ideal for developers who want to create cutting-edge web applications.",
                imageUrl = "https://www.classcentral.com/report/wp-content/uploads/2022/08/BCG-Kotlint-Featured-Banner.png"
            ),
            Course(
                courseCode = 105,
                title = "Data Science Fundamentals",
                instructor = "Dr. Laura Johnson",
                description = "Gain a strong foundation in data science with this course taught by Dr. Laura Johnson. Learn key concepts such as data manipulation, visualization, and analysis. Explore tools like pandas, numpy, and matplotlib to work with data effectively. By the end of the course, you'll be equipped to tackle data-driven projects and derive insights from data.",
                imageUrl = "https://www.classcentral.com/report/wp-content/uploads/2022/08/BCG-Kotlint-Featured-Banner.png"
            ),
            Course(
                courseCode = 106,
                title = "Digital Marketing Strategies",
                instructor = "Samantha Lee",
                description = " Master digital marketing strategies in this course by Samantha Lee. Learn how to leverage social media, SEO, and content marketing to grow your brand. You'll work on real-world projects to apply the concepts you learn. This course is perfect for marketers, entrepreneurs, and anyone looking to enhance their online presence.",
                imageUrl = "https://www.classcentral.com/report/wp-content/uploads/2022/08/BCG-Kotlint-Featured-Banner.png"
            )
        )
    }

}
