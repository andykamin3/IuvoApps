package com.andreskaminker.iuvocare.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.andreskaminker.iuvocare.MainActivity
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.entities.DateResult
import com.andreskaminker.iuvoshared.entities.Patient
import com.andreskaminker.iuvocare.helpers.DummyData
import com.andreskaminker.iuvocare.modules.fabButtonHelpers
import com.andreskaminker.iuvocare.room.viewmodel.AppointmentViewModel
import com.andreskaminker.iuvocare.room.viewmodel.PatientViewModel
import com.andreskaminker.iuvocare.ui.dialogs.DatePickerFragment
import com.andreskaminker.iuvocare.ui.dialogs.TimePickerFragment
import com.andreskaminker.iuvoshared.helpers.FormatUtils
import com.andreskaminker.iuvoshared.helpers.mapToABPMonth
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_appointment.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import kotlin.math.min


class AddAppointmentFragment : Fragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var v: View
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText

    private val TAG = "AddAppointmentFragment"
    private var dateResult = DateResult()
    private lateinit var patient: Patient
    private var timeSetted = false
    private var dateSetted = false
    private lateinit var fabAppointment : FloatingActionButton
    private lateinit var timeSetTextView: TextView
    private lateinit var dateSetTextView: TextView
    private val appointmentViewModel: AppointmentViewModel by activityViewModels()
    private val patientViewModel: PatientViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_add_appointment, container, false)
        timeButton = v.findViewById(R.id.timeButton)
        dateButton = v.findViewById(R.id.dateButton)
        nameEditText = v.findViewById(R.id.editTextMedicationName)
        descriptionEditText = v.findViewById(R.id.editTextMedicationDescription)
        fabAppointment = v.findViewById(R.id.fabAddAppointment)
        timeSetTextView = v.findViewById(R.id.textViewTimeSetted)
        dateSetTextView = v.findViewById(R.id.textViewDateSetted)
        fabAppointment.isEnabled = false
        return v
    }

    private fun updateUI() {
        val mActivity = requireActivity() as MainActivity

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "Hour is $hourOfDay and minutes are $minute")
        dateResult.apply {
            mHour = hourOfDay
            mMinutes = minute
        }
        val toShow = LocalTime.of(hourOfDay, minute)
        timeSetTextView.text = toShow.format(FormatUtils.timeFormatter)

        timeSetted = true
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d(TAG, "Year is $year, month: $month, $dayOfMonth")
        dateResult.apply {
            mDay = dayOfMonth
            mYear = year
            mMonth = month
        }
        val toShow = LocalDate.of(year, mapToABPMonth(month), dayOfMonth)
        dateSetTextView.text = toShow.format(FormatUtils.selectionFormatter)
        dateSetted = true
    }

    override fun onStart() {
        patientViewModel.patient.observe(viewLifecycleOwner, Observer {patient->
            if(patient != null){
                Log.d(TAG, patient.toString())
                fabAppointment.isEnabled = true
                fabAppointment.setOnClickListener() {
                    addAppointment(patient)
                }



            }else{
                Log.d(TAG, "Patient is null")
            }
        })
        dateButton.setOnClickListener {
            DatePickerFragment()
                .show(childFragmentManager, "timePicker")
        }

        timeButton.setOnClickListener {
            TimePickerFragment()
                .show(childFragmentManager, "timePicker")
        }
        updateUI()

        super.onStart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    private fun addAppointment(patient: Patient) {
        val appointmentName = editTextMedicationName.text.toString()
        val appointmentDescription = editTextMedicationDescription.text.toString()
        if (appointmentName != "" && timeSetted && dateSetted) {
            val mAppointment =
                Appointment(
                    "",
                    appointmentDescription,
                    appointmentName,
                    patient,
                    dateResult
                )
            appointmentViewModel.addAppointment(mAppointment)

            val directions =
                AddAppointmentFragmentDirections.actionAddAppointmentFragmentToHomeTabbedScreen()
            v.findNavController().navigate(directions)
        } else {
            Snackbar.make(v, "Completa todos los campos", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToAuth() {
        //TODO: Go back to auth
    }


}