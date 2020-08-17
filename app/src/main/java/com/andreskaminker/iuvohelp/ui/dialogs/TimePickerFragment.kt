package com.andreskaminker.iuvohelp.ui.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment : DialogFragment(), OnTimeSetListener {
    lateinit var mCallback: OnTimeSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        try {
            mCallback = parentFragment as OnTimeSetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnTimePickedListener.")
        }
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val minutes = c.get(Calendar.MINUTE)
        val hour = c.get(Calendar.HOUR_OF_DAY)

        // Create a new instance of DatePickerDialog and return it
        return TimePickerDialog(requireContext(), this, hour, minutes, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (mCallback != null) {
            mCallback.onTimeSet(view, hourOfDay, minute)
        }
    }


}