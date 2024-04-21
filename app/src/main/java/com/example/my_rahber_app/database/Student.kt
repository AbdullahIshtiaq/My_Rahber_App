package com.example.my_rahber_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var email: String,
    var enrolledCourses: String
)
