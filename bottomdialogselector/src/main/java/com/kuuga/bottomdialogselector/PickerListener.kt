package com.kuuga.bottomdialogselector

interface PickerListener<T> {
    fun onDone(position: Int, display: T)
    fun onCancel()
}