package com.kuuga.bottomdialogselector

import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuuga.bottomdialogselector.databinding.DialogPickerBinding
import com.wx.wheelview.adapter.BaseWheelAdapter
import com.wx.wheelview.widget.WheelView

class PickerDialog<T> : BottomSheetDialogFragment() {
    private var listener: PickerListener<T>? = null

    var show = false

    var data: ArrayList<T> = arrayListOf()
        set(value) {
            field = value
            if (binding != null) binding!!.picker.setWheelData(value)
        }

    var position = -1
        set(value) {
            field = value
            if (binding != null) binding!!.picker.selection = value
        }

    var size = 18f
        set(value) {
            field = value
            if (binding != null) {
                setTextSize(binding!!.tvDone, if (size > 2) size - 2f else size)
                setTextSize(binding!!.tvCancel, if (size > 2) size - 2f else size)
                setTextSize(binding!!.tvTitle, if (size > 2) size - 2f else size)
            }
        }

    var loop = false
        set(value) {
            field = value
            if (binding != null) binding!!.picker.setLoop(value)
        }

    var style = WheelView.WheelViewStyle()
        set(value) {
            field = value
            if (binding != null) binding!!.picker.style = value
        }

    var wheelSize = 3
        set(value) {
            field = value
            if (binding != null) binding!!.picker.setWheelSize(value)
        }

    var adapter: BaseWheelAdapter<T>? = null
        set(value) {
            field = value
            if (binding != null && value != null) binding!!.picker.setWheelAdapter(value)
        }


    private var binding: DialogPickerBinding? = null
    private var txtDone: String? = null
    private var txtCancel: String? = null
    private var txtTitle: String? = null
    private var font: Typeface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_picker, container, false)
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

    fun setFont(typeface: Typeface) {
        font = typeface
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

            if (font != null) {
                binding!!.tvDone.setTypeface(font, Typeface.NORMAL)
                binding!!.tvCancel.setTypeface(font, Typeface.NORMAL)
                binding!!.tvTitle.setTypeface(font, Typeface.NORMAL)
            }

            if (adapter != null) binding!!.picker.setWheelAdapter(adapter)

            binding!!.picker.setLoop(loop)
            binding!!.picker.setWheelSize(wheelSize)
            binding!!.picker.skin = WheelView.Skin.Holo
            binding!!.picker.style = style
            binding!!.picker.setWheelData(data)

            if (position > -1)
                binding!!.picker.selection = position

            binding!!.btnDone.setOnClickListener {
                listener!!.onDone(binding!!.picker.currentPosition, binding!!.picker.selectionItem as T)
                dismiss()
            }

            binding!!.btnCancel.setOnClickListener {
                listener!!.onCancel()
                dismiss()
            }
        }
    }


    companion object {
        fun <T> newInstance(listener: PickerListener<T>): PickerDialog<T> {
            val fragment = PickerDialog<T>()
            fragment.listener = listener
            return fragment
        }
    }
}