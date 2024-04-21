package com.example.my_rahber_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_rahber_app.Constant.Companion.STUDENT_DATABASE

@Database(entities = [Student::class, Course::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
}

object DatabaseProvider {

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            STUDENT_DATABASE
        ).build()
    }
}
