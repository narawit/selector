package com.kuuga.bottomdialogselector

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
            if (binding != null) {
                setTextSize(binding!!.tvDone, if (size > 2) size - 2f else size)
                setTextSize(binding!!.tvCancel, if (size > 2) size - 2f else size)
                setTextSize(binding!!.tvTitle, if (size > 2) size - 2f else size)
            }
        }

    private var binding: DialogDatePickerBinding? = null
    private var txtDone: String? = null
    private var txtCancel: String? = null
    private var txtTitle: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_date_picker, container, false)
        binding?.txtDone = txtDone
        binding?.txtCancel = txtCancel
        binding?.txtTitle = txtTitle
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
        if (binding != null) {
            setTextSize(binding!!.tvDone, if (size > 2) size - 2f else size)
            setTextSize(binding!!.tvCancel, if (size > 2) size - 2f else size)
            setTextSize(binding!!.tvTitle, if (size > 2) size - 2f else size)

            val yearAdapter = YearAdapter(context!!, isThai)
            binding!!.yearPicker.setWheelAdapter(yearAdapter)
            val monthAdapter = MonthAdapter(context!!)
            binding!!.monthPicker.setWheelAdapter(monthAdapter)
            val dayAdapter = DayAdapter(context!!)
            binding!!.dayPicker.setWheelAdapter(dayAdapter)


            binding!!.yearPicker.setLoop(false)
            binding!!.monthPicker.setLoop(true)
            binding!!.dayPicker.setLoop(true)

            binding!!.yearPicker.setWheelSize(3)
            binding!!.monthPicker.setWheelSize(3)
            binding!!.dayPicker.setWheelSize(3)

            binding!!.yearPicker.skin = WheelView.Skin.Holo
            binding!!.monthPicker.skin = WheelView.Skin.Holo
            binding!!.dayPicker.skin = WheelView.Skin.Holo

            if (date == null) {
                val date = Calendar.getInstance()
                val year = date.get(Calendar.YEAR)
                val month = date.get(Calendar.MONTH)
                val day = date.get(Calendar.DAY_OF_MONTH)
                this.date = Date(day, Month(month + 1, if (isThai) month_th[month] else month_en[month]), Year(year))
            }
            this.temp = Date(date!!)
            createYearData()
            createMonthData()
            createDayData()
            binding!!.yearPicker.setWheelData(mYear)
            binding!!.monthPicker.setWheelData(mMonth)
            binding!!.dayPicker.setWheelData(mDay)

            binding!!.yearPicker.setOnWheelItemSelectedListener { position, t ->
                if (date!!.year != mYear!![position]) {
                    date!!.year = mYear!![position]
                    val temp = binding!!.dayPicker.currentPosition
                    binding!!.dayPicker.selection = 27
                    createDayData()
                    if (temp > mDay!!.size - 1) {
                        binding!!.dayPicker.selection = mDay!!.size - 1
                    } else {
                        binding!!.dayPicker.selection = temp
                    }
                }
            }

            binding!!.monthPicker.setOnWheelItemSelectedListener { position, t ->
                if (date!!.month != mMonth!![position]) {
                    date!!.month = mMonth!![position]
                    val temp = binding!!.dayPicker.currentPosition
                    binding!!.dayPicker.selection = 27
                    createDayData()
                    if (temp > mDay!!.size - 1) {
                        binding!!.dayPicker.selection = mDay!!.size - 1
                    } else {
                        binding!!.dayPicker.selection = temp
                    }
                }
            }

            binding!!.dayPicker.setOnWheelItemSelectedListener { position, t ->
                date!!.day = mDay!![position]
            }

            binding!!.yearPicker.selection = checkYear(date!!.year)
            binding!!.monthPicker.selection = checkMonth(date!!.month)
            binding!!.dayPicker.selection = checkDay(date!!.day)

            binding!!.btnDone.setOnClickListener {
                listener!!.onDone(date!!)
                dismiss()
            }

            binding!!.btnCancel.setOnClickListener {
                date = Date(temp!!)
                listener!!.onCancel()
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

    var mDay: ArrayList<Int>? = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)
    private fun createDayData() {
        when (date!!.month.id) {
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
                if (date!!.year.year % 4 == 0) {
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
        private val month_th = arrayListOf("มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม")
        private val month_en = arrayListOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

        private fun createDateFromInt(isThai: Boolean, year: Int, month: Int, day: Int): Date {
            return Date(day, Month(month, if (isThai) month_th[month - 1] else month_en[month - 1]), Year(year))
        }

        fun newInstance(listener: DatePickerListener, isThai: Boolean, year: Int? = null, month: Int? = null, day: Int? = null): DatePickerDialog {
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