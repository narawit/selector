package com.kuuga.bottomdialogselector

interface SelectorListener {
    fun onDone(position: Int, display: String)
    fun onCancel()
}