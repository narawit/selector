package com.kuuga.bottomdialogselector

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuuga.bottomdialogselector.databinding.BottomDialogBinding

class SelectorFragment : BottomSheetDialogFragment() {
    private var listener: SelectorListener? = null
    private var isCancel = false

    var data: ArrayList<String> = arrayListOf()
        set(value) {
            field = value
            if (binding != null) binding!!.textPicker.setData(value)
        }

    var position = -1
        set(value) {
            field = value
            if (binding != null) binding!!.textPicker.value = value
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

    private var binding: BottomDialogBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_dialog, container, false)
        return binding!!.root
    }

    fun setTextDone(text: String) {
        if (binding != null) {
            binding!!.tvDone.text = text
        }
    }

    fun setTextCancel(text: String) {
        if (binding != null) {
            binding!!.tvCancel.text = text
        }
    }

    fun setTextTitle(text: String) {
        if (binding != null) {
            binding!!.tvTitle.text = text
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if (isCancel) listener!!.onCancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding != null) {
            setTextSize(binding!!.tvDone, if (size > 2) size - 2f else size)
            setTextSize(binding!!.tvCancel, if (size > 2) size - 2f else size)
            setTextSize(binding!!.tvTitle, if (size > 2) size - 2f else size)

            binding!!.textPicker.setData(data)

            if (position > -1)
                binding!!.textPicker.value = position

            binding!!.btnDone.setOnClickListener {
                listener!!.onDone(binding!!.textPicker.value, data[binding!!.textPicker.value])
                dismiss()
            }

            binding!!.btnCancel.setOnClickListener {
                isCancel = true
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(listener: SelectorListener): SelectorFragment {
            val fragment = SelectorFragment()
            fragment.listener = listener
            fragment.isCancel = false
            return fragment
        }
    }
}