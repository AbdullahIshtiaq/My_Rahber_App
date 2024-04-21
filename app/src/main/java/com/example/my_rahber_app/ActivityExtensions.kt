package com.example.my_rahber_app

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import com.example.my_rahber_app.Constant.Companion.DATA
import java.io.Serializable

inline fun <reified T> Activity.navigateWithData(
    data: Serializable? = null,
) {
    val intent = Intent(this, T::class.java)
    data?.let {
        intent.putExtra(DATA, it)
    }
    startActivity(intent)
}


inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

