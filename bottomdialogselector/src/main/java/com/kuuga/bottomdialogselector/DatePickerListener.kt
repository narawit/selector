package com.kuuga.bottomdialogselector

import com.kuuga.bottomdialogselector.model.Date

interface DatePickerListener {
    fun onDone(data: Date)
    fun onCancel()
}
