package com.kuuga.selector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selector.display = "Country"
        selector.data = arrayListOf("Thailand","USA","Japan")

        selector.setOnSelectListener { position, display ->
            Toast.makeText(this,"position:$position , display:$display",Toast.LENGTH_LONG).show()
        }
    }
}
