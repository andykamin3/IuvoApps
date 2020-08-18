package com.andreskaminker.iuvocare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvocare.helpers.MedicationAdapter
import com.andreskaminker.iuvocare.modules.MedicationFragmentFunctions
import com.andreskaminker.iuvocare.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvocare.ui.dialogs.ConfirmDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SeeMedicationFragment : Fragment(), MedicationFragmentFunctions {
    private lateinit var v: View
    private lateinit var medicationAdapter: MedicationAdapter
    private lateinit var medicationList: MutableList<MedicationRequest>
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabButton: FloatingActionButton

    private val medicationViewModel: MedicationViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_see_medication, container, false)
        recyclerView = v.findViewById(R.id.recyclerMedications)
        fabButton = v.findViewById(R.id.fabSeeMedication)
        return v
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SeeMedicationFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medicationList = mutableListOf()
        medicationAdapter = MedicationAdapter(this)
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = medicationAdapter
            layoutManager = LinearLayoutManager(context)
        }
        updateUI()
        medicationViewModel.allMedications.observe(
            viewLifecycleOwner,
            Observer { medicationsList ->
                medicationsList?.let {
                    medicationAdapter.setData(it)
                }
            })
    }




    private fun updateUI() {
        //mActivity.setFabColor(R.color.colorAccent)
        fabButton.setOnClickListener {
            val directions =
                HomeTabbedScreenDirections.actionHomeTabbedScreenToAddMedicationFragment()
            v.findNavController().navigate(directions)
        }
    }

    override fun deleteMedication(medicationRequest: MedicationRequest) {
        ConfirmDialog("Confirmar borrado de medicación") { onDeleteConfirmed(medicationRequest) }.show(
            childFragmentManager,
            "confirmDeleteFrg"
        )
    }

    private fun onDeleteConfirmed(medicationRequest: MedicationRequest) {
        medicationViewModel.deleteMedication(medicationRequest)
    }

}