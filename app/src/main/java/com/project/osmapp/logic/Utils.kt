package com.project.osmapp.logic

import android.app.Activity
import android.content.Context
import android.content.Intent

fun restartActivity(context: Context) {
    val intent = (context as Activity).intent
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    context.finish()
}