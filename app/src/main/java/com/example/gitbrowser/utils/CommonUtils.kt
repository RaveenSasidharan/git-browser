package com.example.gitbrowser.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

@SuppressLint("StaticFieldLeak")
object CommonUtils {

    public lateinit var context: Context


    fun checkConnection(): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}