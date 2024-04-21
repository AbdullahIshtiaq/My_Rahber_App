package com.example.my_rahber_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseCode: Int,
    val title: String,
    val instructor: String,
    val description: String,
    val imageUrl: String,
) : Serializable
