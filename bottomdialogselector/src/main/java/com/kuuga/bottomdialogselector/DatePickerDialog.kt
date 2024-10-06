package com.kuuga.bottomdialogselector

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuuga.bottomdialogselector.adapter.DayAdapter
import com.kuuga.bottomdialogselector.adapter.MonthAdapter
import com.kuuga.bottomdialogselector.adapter.YearAdapter
import com.kuuga.bottomdialogselector.databinding.DialogDatePickerBinding
import com.kuuga.bottomdialogselector.model.Date
import com.kuuga.bottomdialogselector.model.Month
import com.kuuga.bottomdialogselector.model.Year
import com.wx.wheelview.widget.WheelView
import java.util.*

class DatePickerDialog : BottomSheetDialogFragment() {
    private var listener: DatePickerListener? = null
    private var isThai: Boolean = false

    private var temp: Date? = null
    var date: Date? = null

    var show = false

    var size = 18f
        set(value) {
            field = value
            binding?.let {
                setTextSize(it.tvDone, if (size > 2) size - 2f else size)
                setTextSize(it.tvCancel, if (size > 2) size - 2f else size)
                setTextSize(it.tvTitle, if (size > 2) size - 2f else size)
            }
        }

    private var binding: DialogDatePickerBinding? = null
    private var txtDone: String? = null
    private var txtCancel: String? = null
    private var txtTitle: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDatePickerBinding.inflate(layoutInflater, container, false)
        txtDone = getString(R.string.done)
        txtCancel = getString(R.string.cancel)
        txtTitle = getString(R.string.title)
        show = true
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        show = false
    }

    fun setTextDone(text: String) {
        txtDone = text
    }

    fun setTextCancel(text: String) {
        txtCancel = text
    }

    fun setTextTitle(text: String) {
        txtTitle = text
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        show = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let{
            setTextSize(it.tvDone, if (size > 2) size - 2f else size)
            setTextSize(it.tvCancel, if (size > 2) size - 2f else size)
            setTextSize(it.tvTitle, if (size > 2) size - 2f else size)

            val yearAdapter = YearAdapter(context!!, isThai)
            it.yearPicker.setWheelAdapter(yearAdapter)
            val monthAdapter = MonthAdapter(context!!)
            it.monthPicker.setWheelAdapter(monthAdapter)
            val dayAdapter = DayAdapter(context!!)
            it.dayPicker.setWheelAdapter(dayAdapter)


            it.yearPicker.setLoop(false)
            it.monthPicker.setLoop(true)
            it.dayPicker.setLoop(true)

            it.yearPicker.setWheelSize(3)
            it.monthPicker.setWheelSize(3)
            it.dayPicker.setWheelSize(3)

            it.yearPicker.skin = WheelView.Skin.Holo
            it.monthPicker.skin = WheelView.Skin.Holo
            it.dayPicker.skin = WheelView.Skin.Holo

            if (date == null) {
                val date = Calendar.getInstance()
                val year = date.get(Calendar.YEAR)
                val month = date.get(Calendar.MONTH)
                val day = date.get(Calendar.DAY_OF_MONTH)
                this.date = Date(
                    day,
                    Month(month + 1, if (isThai) month_th[month] else month_en[month]),
                    Year(year)
                )
            }
            this.temp = Date(date!!)
            createYearData()
            createMonthData()
            createDayData()
            it.yearPicker.setWheelData(mYear)
            it.monthPicker.setWheelData(mMonth)
            it.dayPicker.setWheelData(mDay)

            it.yearPicker.setOnWheelItemSelectedListener { position, t ->
                if (this.temp!!.year != mYear!![position]) {
                    this.temp!!.year = mYear!![position]
                    val temp = it.dayPicker.currentPosition
                    it.dayPicker.selection = 27
                    createDayData()
                    if (temp > mDay!!.size - 1) {
                        it.dayPicker.selection = mDay!!.size - 1
                    } else {
                        it.dayPicker.selection = temp
                    }
                }
            }

            it.monthPicker.setOnWheelItemSelectedListener { position, t ->
                if (this.temp!!.month != mMonth!![position]) {
                    this.temp!!.month = mMonth!![position]
                    val temp = it.dayPicker.currentPosition
                    it.dayPicker.selection = 27
                    createDayData()
                    if (temp > mDay!!.size - 1) {
                        it.dayPicker.selection = mDay!!.size - 1
                    } else {
                        it.dayPicker.selection = temp
                    }
                }
            }

            it.dayPicker.setOnWheelItemSelectedListener { position, t ->
                this.temp!!.day = mDay!![position]
            }

            it.yearPicker.selection = checkYear(this.temp!!.year)
            it.monthPicker.selection = checkMonth(this.temp!!.month)
            it.dayPicker.selection = checkDay(this.temp!!.day)

            it.btnDone.setOnClickListener {
                date = Date(temp!!)
                listener?.onDone(date!!)
                dismiss()
            }

            it.btnCancel.setOnClickListener {
                listener?.onCancel()
                dismiss()
            }
        }
    }

    private fun checkYear(year: Year): Int {
        return mYear?.indexOf(year) ?: 0
    }

    private fun checkMonth(month: Month): Int {
        return mMonth?.indexOf(month) ?: 0
    }

    private fun checkDay(day: Int): Int {
        return mDay?.indexOf(day) ?: 0
    }

    var mDay: ArrayList<Int>? = arrayListOf(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28
    )

    private fun createDayData() {
        when (this.temp!!.month.id) {
            1, 3, 5, 7, 8, 10, 12 -> {
                mDay!!.remove(31)
                mDay!!.remove(30)
                mDay!!.remove(29)

                mDay!!.add(29)
                mDay!!.add(30)
                mDay!!.add(31)
            }

            2 -> {
                mDay!!.remove(31)
                mDay!!.remove(30)
                mDay!!.remove(29)
                if (this.temp!!.year.year % 4 == 0) {
                    mDay!!.add(29)
                }
            }

            else -> {
                mDay!!.remove(31)
                mDay!!.remove(30)
                mDay!!.remove(29)

                mDay!!.add(29)
                mDay!!.add(30)
            }
        }
    }

    var mMonth: ArrayList<Month>? = null
    private fun createMonthData() {
        if (mMonth == null) {
            mMonth = arrayListOf()
            var index = 1
            if (isThai) {
                month_th.forEach {
                    mMonth!!.add(Month(index++, it))
                }
            } else {
                month_en.forEach {
                    mMonth!!.add(Month(index++, it))
                }
            }
        }
    }

    var mYear: ArrayList<Year>? = null
    private fun createYearData() {
        if (mYear == null) {
            val date = Calendar.getInstance()
            val year = date.get(Calendar.YEAR)
            val minYear = year - 300

            mYear = arrayListOf()
            var index = minYear
            while (index <= year) {
                mYear!!.add(Year(index++))
            }
        }
    }

    companion object {
        private val month_th = arrayListOf(
            "มกราคม",
            "กุมภาพันธ์",
            "มีนาคม",
            "เมษายน",
            "พฤษภาคม",
            "มิถุนายน",
            "กรกฎาคม",
            "สิงหาคม",
            "กันยายน",
            "ตุลาคม",
            "พฤศจิกายน",
            "ธันวาคม"
        )
        private val month_en = arrayListOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )

        fun createDateFromInt(isThai: Boolean, year: Int, month: Int, day: Int): Date {
            return Date(
                day,
                Month(month, if (isThai) month_th[month - 1] else month_en[month - 1]),
                Year(year)
            )
        }

        fun newInstance(
            listener: DatePickerListener,
            isThai: Boolean,
            year: Int? = null,
            month: Int? = null,
            day: Int? = null
        ): DatePickerDialog {
            val fragment = DatePickerDialog()
            fragment.listener = listener
            fragment.isThai = isThai
            if (year != null && month != null && day != null) {
                fragment.date = createDateFromInt(isThai, year, month, day)
            }
            return fragment
        }
    }
}