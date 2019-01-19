package com.example.haris.finalproject.utils

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.gone(){
        visibility = View.GONE
    }

    fun toMillis(strDate: String, strTime: String): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.US)
        val date = simpleDateFormat.parse("$strDate $strTime")
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.timeInMillis
    }

    fun dateFormat(strDate: String, strTime: String): Date {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.US)
        val date = simpleDateFormat.parse("$strDate $strTime")
        return date
    }



