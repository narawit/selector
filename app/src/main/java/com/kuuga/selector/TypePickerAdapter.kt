package com.kuuga.selector

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kuuga.selector.databinding.ItemPickerTextBinding
import com.wx.wheelview.adapter.BaseWheelAdapter


class TypePickerAdapter(private val mContext: Context) : BaseWheelAdapter<String>() {

    override fun bindView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val binding: ItemPickerTextBinding

        if (convertView == null) {
            viewHolder = ViewHolder()
            binding = ItemPickerTextBinding.inflate(
                LayoutInflater.from(mContext),
                parent,
                false
            )

            viewHolder.textView = binding.itemName
            binding.root.tag = viewHolder
        } else {
            binding = ItemPickerTextBinding.bind(convertView)
            viewHolder = binding.root.tag as ViewHolder
        }

        viewHolder.textView!!.text = mList[position]

        return binding.root
    }

    class ViewHolder {
        var textView: TextView? = null
    }

}
