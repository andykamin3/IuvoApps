package com.andreskaminker.iuvohelp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreskaminker.iuvohelp.MainActivity
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvohelp.databinding.FragmentSeeAppointmentBinding
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvohelp.helpers.AppointmentAdapter
import com.andreskaminker.iuvohelp.modules.AppointmentFragmentFunctions
import com.andreskaminker.iuvohelp.room.viewmodel.AppointmentViewModel
import com.andreskaminker.iuvohelp.ui.dialogs.ConfirmDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SeeAppointmentFragment : Fragment(), AppointmentFragmentFunctions {

    var _binding: FragmentSeeAppointmentBinding? = null
    val binding get() = _binding!!
    private lateinit var fabButton: FloatingActionButton
    private val appointmentViewModel: AppointmentViewModel by activityViewModels()

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

        appointmentViewModel.allAppointments.observe(viewLifecycleOwner, Observer { appointments ->
            appointments?.let { mAdapter.setData(appointments) }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    companion object {

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