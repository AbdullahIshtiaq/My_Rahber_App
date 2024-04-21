package com.example.my_rahber_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_rahber_app.database.Course

class CourseAdapter(val adapterCallback: (Any) -> Unit) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var courses = emptyList<Course>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course_recycler, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.apply {
            courseTitle.text = courses[position].title
            courseDescription.text = courses[position].description
            courseInstructor.text = courses[position].instructor

            Glide.with(itemView.context)
                .load(courses[position].imageUrl)
                .into(courseImage)

            courseImage.clipToOutline = true

            itemView.setOnClickListener {
                adapterCallback(courses[position])
            }
        }


    }

    override fun getItemCount(): Int {
        return courses.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCourses(courses: List<Course>) {
        this.courses = courses
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val courseTitle: TextView = itemView.findViewById(R.id.course_title)
        val courseDescription: TextView = itemView.findViewById(R.id.course_description)
        val courseImage: ImageView = itemView.findViewById(R.id.course_image)
        val courseInstructor: TextView = itemView.findViewById(R.id.course_instructor)
    }
}