package com.example.urbandictionaryapp.core

import java.text.SimpleDateFormat
import java.util.*

const val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}