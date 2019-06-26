package com.kuuga.selector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kuuga.bottomdialogselector.DatePickerDialog
import com.kuuga.bottomdialogselector.DatePickerListener
import com.kuuga.bottomdialogselector.model.Date
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selector = DatePickerDialog.newInstance(object :DatePickerListener{
            override fun onDone(data: Date) {
                tv_date.text= "${data.day} ${data.month.name} ${data.year.year}"
            }

            override fun onCancel() {

            }

        },true)
        btn_dialog.setOnClickListener {
            selector.show(supportFragmentManager,"dialog")
        }
    }
}
