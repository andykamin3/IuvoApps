package com.andreskaminker.iuvohelp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.andreskaminker.iuvohelp.R
import com.google.android.material.snackbar.Snackbar

class ConfirmDialog(val prompt: String, val onSuccess: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it, STYLE_NORMAL)
            builder.setMessage(prompt)
                .setPositiveButton(
                    R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        // FIRE ZE MISSILES!
                        onSuccess()
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        Snackbar.make(
                            requireView(),
                            R.string.action_canceled,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}