package com.kuuga.bottomdialogselector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kuuga.bottomdialogselector.databinding.SimplePickerTextBinding
import com.kuuga.bottomdialogselector.setTextSize
import com.wx.wheelview.adapter.BaseWheelAdapter

class DayAdapter(private val mContext: Context) : BaseWheelAdapter<Int>() {

    override fun bindView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = ViewHolder()
        val binding: SimplePickerTextBinding =
            SimplePickerTextBinding.inflate(LayoutInflater.from(mContext), parent, false)
        viewHolder.textView = binding.itemName
        viewHolder.textView?.let { setTextSize(it,20f) }
        viewHolder.textView?.text = mList[position].toString()
        return binding.root
    }

    class ViewHolder {
        var textView: TextView? = null
    }
}