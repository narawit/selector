package com.kuuga.bottomdialogselector

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView

fun setTextSize(view: TextView, size: Float) {
    val heightDp: Float
    val heightPixels: Int
    val widthPixels: Int
    val scaleFontFactor: Float
    val widthDp: Float

    val windowManager = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    windowManager.getDefaultDisplay().getMetrics(metrics)

    scaleFontFactor = metrics.scaledDensity

    heightPixels = metrics.heightPixels
    widthPixels = metrics.widthPixels
    heightDp = heightPixels / scaleFontFactor
    widthDp = widthPixels / scaleFontFactor
    val diffHeightDp: Float

    diffHeightDp = if (heightPixels >= widthPixels) {
        // portrait
        heightDp / 683.4286f
    } else {
        // landscape
        widthDp / 411.42856f
    }

    view.textSize = size / diffHeightDp * heightDp
}
