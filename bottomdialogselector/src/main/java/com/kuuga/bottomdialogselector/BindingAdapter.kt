package com.kuuga.bottomdialogselector

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.BindingAdapter

fun setTextSize(view: TextView, size: Float) {
    val scaleFactor: Float
    val heightDp: Float
    val widthInches: Float
    val widthDpi: Float
    val heightDpi: Float
    val heightInches: Float
    val diagonalInches: Double
    val heightPixels: Int
    val widthPixels: Int
    val width: Int
    val height: Int

    val windowManager = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    width = windowManager.defaultDisplay.width
    height = windowManager.defaultDisplay.height
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)

    heightPixels = metrics.heightPixels
    scaleFactor = metrics.density
    widthPixels = metrics.widthPixels
    heightDp = heightPixels / scaleFactor
    widthDpi = metrics.xdpi
    heightDpi = metrics.ydpi
    widthInches = widthPixels / widthDpi
    heightInches = heightPixels / heightDpi
    diagonalInches = Math.sqrt((widthInches * widthInches + heightInches * heightInches).toDouble())

    val diffHeightDp: Float

    diffHeightDp = if (height >= width) {
        if (height < 801 && diagonalInches < 6.0) {
            800.0f
        } else {
            592.0f
        }
    } else {
        if (height < 801 && diagonalInches < 6.0) {
            560.0f
        } else {
            360.0f
        }
    }

    view.textSize = size / diffHeightDp * heightDp
}
