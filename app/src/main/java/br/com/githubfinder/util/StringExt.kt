package br.com.githubfinder.util

import android.annotation.SuppressLint
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.brazilDateFormat(): String {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val output = SimpleDateFormat("dd-MM-yyyy")
    var parseResult = Date()

    try {
        parseResult = input.parse(this)!!

    } catch (e: ParseException) {
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        Log.e("Repository Fragment", sw.toString())
    }

    return output.format(parseResult)
}