package org.succlz123.lib.context

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.MainThread

private var applicationContext: Context? = null

@SuppressLint("PrivateApi")
@MainThread
fun getApplicationContext(): Context? {
    if (applicationContext != null) {
        return applicationContext
    }
    return try {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getMethod("currentApplication")
        val ctx = method.invoke(null) as? Context
        applicationContext = ctx
        ctx
    } catch (e: Exception) {
        null
    }
}