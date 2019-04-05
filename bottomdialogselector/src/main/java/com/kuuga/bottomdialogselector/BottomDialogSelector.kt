package com.kuuga.bottomdialogselector

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class BottomDialogSelector : TextView, SelectorListener {
    var pos = -1
    var display = "Selector"
        set(value) {
            field = value
            text = value
        }

    var data = arrayListOf<String>()
        set(value) {
            field = value
            if (selector != null) selector!!.data = data
        }

    var txtDone = ""
        set(value) {
            field = value
            if (selector != null) selector!!.setTextDone(value)
        }

    var txtCancel = ""
        set(value) {
            field = value
            if (selector != null) selector!!.setTextCancel(value)
        }

    var txtTitle = ""
        set(value) {
            field = value
            if (selector != null) selector!!.setTextTitle(value)
        }

    var txtDialogSize = 18f
        set(value) {
            field = value
            if (selector != null) selector!!.size = value
        }

    private var supportFragmentManager: FragmentManager? = null
    private var selector: SelectorFragment? = null

    fun setPosition(position: Int) {
        this.pos = position
        if (selector != null)
            selector!!.position = position
    }

    constructor(context: Context) : super(context) {
        initUI()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initUI()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        initUI()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        initUI()
    }


    private fun initUI() {
        text = display
        selector = SelectorFragment.newInstance(this)
        setOnClickListener {
            if (isFragmentActivity()) {
                selector!!.show(supportFragmentManager, "selector")
            }
        }
    }

    private fun isFragmentActivity(): Boolean {
        return try {
            val activity = context as FragmentActivity
            supportFragmentManager = activity.supportFragmentManager
            true
        } catch (e: ClassCastException) {
            Log.e(TAG, "Can't get the fragment manager with this")
            false
        }
    }

    fun setOnSelectListener(listener: (position: Int, display: String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.invoke(pos, display)
            }
        })
    }

    override fun onDone(position: Int, display: String) {
        this.pos = position
        this.display = display

        this.text = display
    }
}