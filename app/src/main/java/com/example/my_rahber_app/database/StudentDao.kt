package com.example.my_rahber_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Query("SELECT * FROM Student WHERE id = :studentId")
    fun getStudentById(studentId: Int): Student

    @Query("SELECT * FROM Student")
    fun getAllStudents(): List<Student>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: Course)

    @Query("SELECT * FROM Course WHERE courseCode = :courseCode")
    fun getCourseByCode(courseCode: Int): Course

    @Query("SELECT * FROM Course")
    fun getAllCourses(): List<Course>

}
