package com.andreskaminker.iuvohelp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvohelp.MainActivity
import com.andreskaminker.iuvohelp.R
import com.andreskaminker.iuvohelp.helpers.MedicationAdapter
import com.andreskaminker.iuvohelp.modules.MedicationFragmentFunctions
import com.andreskaminker.iuvohelp.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvohelp.ui.dialogs.ConfirmDialog
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SeeMedicationFragment : Fragment(), MedicationFragmentFunctions {
    private lateinit var v: View
    private lateinit var medicationAdapter: MedicationAdapter
    private lateinit var medicationList: MutableList<MedicationRequest>
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnVerMasMeds: Button


    private val medicationViewModel: MedicationViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_see_medication, container, false)
        recyclerView = v.findViewById(R.id.recyclerMedications)
        btnVerMasMeds = v.findViewById(R.id.btnMedsMas)
        btnVerMasMeds.setOnClickListener {
            Navigation.findNavController(v).navigate(R.id.action_seeMedicationFragment_to_addMedicationFragment)
        }
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
        medicationViewModel.allMedications.observe(
            viewLifecycleOwner,
            Observer { medicationsList ->
                medicationsList?.let {
                    medicationAdapter.setData(it as MutableList<MedicationRequest>)
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }





    override fun deleteMedication(medicationRequest: MedicationRequest) {
        ConfirmDialog("Confirmar borrado de medicación") { onDeleteConfirmed(medicationRequest) }.show(
            childFragmentManager,
            "confirmDeleteFrg"
        )
    }

    fun onDeleteConfirmed(medicationRequest: MedicationRequest) {
        medicationViewModel.deleteMedication(medicationRequest)
    }

}