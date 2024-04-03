package com.example.weather.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.weather.R
import java.util.Calendar


fun Fragment.replaceFragment(fragment: Fragment) {
    if (!(fragment.isAdded))
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).addToBackStack(null)
            .commit()
}

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Note: Months start from 0
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "$year-$month-$day"
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun converToCelisuse(fahrenheit: Float): String {
    var celisuse = (fahrenheit - 273.15)
    val resultOfConvert = "%.0f".format(celisuse)
    return "$resultOfConvert°C"
}