package com.kuuga.bottomdialogselector.model

data class Date(
        var day: Int,
        var month: Month,
        var year: Year
) {
    constructor(date: Date) : this(date.day, date.month, date.year)
}