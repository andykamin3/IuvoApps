package com.andreskaminker.iuvohelp.ui

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import ca.antonious.materialdaypicker.MaterialDayPicker
import com.andreskaminker.iuvohelp.MainActivity
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvoshared.entities.Patient
import com.andreskaminker.iuvoshared.entities.TimeResult
import com.andreskaminker.iuvohelp.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvohelp.ui.dialogs.TimePickerFragment
import com.google.android.material.snackbar.Snackbar


class AddMedicationFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private val TAG = "AddMedicationFragment"
    private lateinit var v: View
    private lateinit var timeButton: Button
    private lateinit var materialPicker: MaterialDayPicker
    private lateinit var timeResult: TimeResult
    private var timeSet = false
    private var imageSet = false
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageButton: Button
    private val medicationViewModel: MedicationViewModel by activityViewModels()
    private val currentPatient = Patient(
        "123",
        "Andy",
        "andykamin3@gmail.com",
        ""
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_medication, container, false)
        materialPicker = v.findViewById(R.id.day_picker)
        timeButton = v.findViewById(R.id.timeButton)
        nameEditText = v.findViewById(R.id.editTextMedicationName)
        descriptionEditText = v.findViewById(R.id.editTextMedicationDescription)
        imageButton = v.findViewById(R.id.imageButton)
        return v
    }

    private fun updateUI() {
        val mActivity = requireActivity() as MainActivity
        mActivity.setFabDrawable(R.drawable.ic_baseline_check_24_b)
        mActivity.setFabColor(R.color.colorAccent)
        mActivity.setFabClickListener {
            addMedication()
        }
    }

    override fun onStart() {
        super.onStart()
        timeButton.setOnClickListener {
            TimePickerFragment()
                .show(childFragmentManager, "timePicker")
        }
        imageButton.setOnClickListener {
            imageSet = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateUI()
    }

    private fun addMedication() {
        super.onStart()
        val medicationName = nameEditText.text.toString()
        val medicationDescription = descriptionEditText.text.toString()
        val weekDays = arrayListOf<Int>()
        materialPicker.selectedDays.map {
            weekDays.add(it.ordinal)
        }

        if (timeSet && medicationName != "" && weekDays.isNotEmpty() && imageSet) {
            val medicationRequest =
                MedicationRequest(
                    id = "generated",
                    patient = currentPatient,
                    medicationName = medicationName,
                    scheduledFor = weekDays,
                    imageUrl = "images.jpg", //TODO: Change image when uploaded
                    takeTime = timeResult
                )
            medicationViewModel.addMedication(medicationRequest)
            (requireActivity() as MainActivity).setFabColor(R.color.colorAccent)
            val directions =
                AddMedicationFragmentDirections.actionAddMedicationFragmentToHomeTabbedScreen()
            v.findNavController().navigate(directions)
        } else {
            Snackbar
                .make(v, "Por favor completar los campos obligatorios", Snackbar.LENGTH_SHORT)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .show()
        }
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        timeResult = TimeResult(
            hour = hourOfDay,
            minutes = minute
        )
        timeSet = true
    }


    //https://github.com/gantonious/MaterialDayPicker for the implementation of the week day picker using Calendar

}