package com.example.my_rahber_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SingleTextAdapter : RecyclerView.Adapter<SingleTextAdapter.TextViewHolder>() {

    private var courses = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_single_text, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.apply {
            courseTitle.text = courses[position]
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCourses(courses: List<String>) {
        this.courses = courses
        notifyDataSetChanged()
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val courseTitle: TextView = itemView.findViewById(R.id.course_title_text_recycler_view)
    }
}
