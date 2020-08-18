package com.andreskaminker.iuvocare.ui.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var mCallback: DatePickerDialog.OnDateSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        try {
            mCallback = parentFragment as DatePickerDialog.OnDateSetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnTimePickedListener.")
        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR)
        val minutes = c.get(Calendar.MINUTE)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        if (mCallback != null) {
            mCallback.onDateSet(view, year, month, day)
        }
    }
}