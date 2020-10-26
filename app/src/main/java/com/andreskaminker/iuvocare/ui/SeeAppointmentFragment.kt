package com.andreskaminker.iuvocare.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvocare.databinding.FragmentSeeAppointmentBinding
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvocare.helpers.AppointmentAdapter
import com.andreskaminker.iuvocare.modules.AppointmentFragmentFunctions
import com.andreskaminker.iuvocare.room.viewmodel.AppointmentViewModel
import com.andreskaminker.iuvocare.room.viewmodel.PatientViewModel
import com.andreskaminker.iuvocare.ui.dialogs.ConfirmDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeeAppointmentFragment : Fragment(), AppointmentFragmentFunctions {

    var _binding: FragmentSeeAppointmentBinding? = null
    val binding get() = _binding!!
    private lateinit var fabButton: FloatingActionButton
    private val appointmentViewModel: AppointmentViewModel by activityViewModels()
    private val patientViewModel: PatientViewModel by activityViewModels()
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeeAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAdapter = AppointmentAdapter(fragment = this)
        binding.recyclerAppointments.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

  



        db.collection("appointments")
            .whereEqualTo("patient.patId", patientViewModel.patient.value?.patId)
            .limit(25)
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w(TAG, error)
                    //TODO: "NOTIFY USER"
                    Snackbar.make(binding.root, "Error obteniendo los datos.", Snackbar.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                if(value?.documents.isNullOrEmpty()){
                    Log.w(TAG, "Firebase is null")
                    //binding.root.background = context?.resources?.getDrawable(com.andreskaminker.iuvoshared.R.drawable.ic_medicamente_01,context?.theme)
                    Snackbar.make(binding.root, "No hay datos disponibles. Agregalos usando el botón", Snackbar.LENGTH_SHORT).show()
                }
                else{
                    value?.toObjects(Appointment::class.java)?.let {
                        //appointmentViewModel.refreshAppointments(it)
                        mAdapter.setData(it)
                        Log.d(TAG, it.toString())
                    }
                }
            }
        updateUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    companion object {
        private const val TAG = "SeeAppointmentFragment"
        @JvmStatic
        fun newInstance() =
            SeeAppointmentFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun updateUI() {


        //mActivity.setFabColor(R.color.colorAccent)
        binding.fabSeeAppointment.setOnClickListener {
            val directions =
                HomeTabbedScreenDirections.actionHomeTabbedScreenToAddAppointmentFragment()
            binding.root.findNavController().navigate(directions)
        }
    }

    fun deleteConfirmed(appointment: Appointment) {
        appointmentViewModel.deleteAppointment(appointment)
    }

    override fun deleteAppointment(appointment: Appointment) {
        ConfirmDialog("Borrar el turno") { deleteConfirmed(appointment) }.show(
            childFragmentManager,
            "confirmDelete"
        )
    }
}