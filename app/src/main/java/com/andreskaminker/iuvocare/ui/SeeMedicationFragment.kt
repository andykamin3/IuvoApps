package com.andreskaminker.iuvocare.ui

import android.app.AlertDialog
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
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvocare.R
import com.andreskaminker.iuvocare.databinding.AddProfileDialogBinding
import com.andreskaminker.iuvocare.databinding.FragmentSeeMedicationBinding
import com.andreskaminker.iuvocare.helpers.MedicationAdapter
import com.andreskaminker.iuvocare.modules.MedicationFragmentFunctions
import com.andreskaminker.iuvocare.room.viewmodel.MedicationViewModel
import com.andreskaminker.iuvocare.room.viewmodel.PatientViewModel
import com.andreskaminker.iuvocare.ui.dialogs.ConfirmDialog
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeeMedicationFragment : Fragment(), MedicationFragmentFunctions {
    private lateinit var v: View
    private lateinit var medicationAdapter: MedicationAdapter
    private lateinit var medicationList: MutableList<MedicationRequest>
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabButton: FloatingActionButton
    private val medicationReference = Firebase.firestore.collection("medications")
    private val medicationViewModel: MedicationViewModel by activityViewModels()
    private val patientViewModel: PatientViewModel by activityViewModels()
    private var _binding : FragmentSeeMedicationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeeMedicationBinding.inflate(inflater, container, false)
        v = binding.root
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
        private val TAG = "SeeMedicationFragment"
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


    }

    override fun onStart() {
        super.onStart()
        patientViewModel.patient.observe(viewLifecycleOwner, Observer {
            val patId = patientViewModel.patient.value?.patId
            if(patId != null){
                medicationReference.whereEqualTo("patient.patId", patId)
                    .addSnapshotListener{value, error ->
                        if(error != null){
                            Log.w(TAG, "Error: $error", error)
                            //TODO:Alert user
                            return@addSnapshotListener;
                        }
                        if(value?.documents.isNullOrEmpty()){
                            binding.root.background = context?.resources?.getDrawable(com.andreskaminker.iuvoshared.R.drawable.ic_medicamente_01,context?.theme)
                            binding.root.contentDescription= resources.getString(R.string.data_not_found)
                            Log.w(TAG, "Firestore collection has no match")
                        } else{
                            binding.root.setBackgroundColor(requireContext().getColor(R.color.colorWhite))
                            binding.root.contentDescription= ""
                            value?.toObjects(MedicationRequest::class.java)?.let {
                                medicationAdapter.setData(it)
                            }
                        }
                    }
            } else{
                binding.root.background = context?.resources?.getDrawable(com.andreskaminker.iuvoshared.R.drawable.ic_medicamente_01,context?.theme)
                binding.root.contentDescription= resources.getString(R.string.data_not_found)
                val viewGroup: ViewGroup = (v.parent as ViewGroup)

                val dialogBinding: AddProfileDialogBinding = AddProfileDialogBinding.inflate(layoutInflater, viewGroup, false)
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                builder.setView(dialogBinding.root)
                val alertDialog = builder.create()
                dialogBinding.addUserButton.setOnClickListener {
                    val directions = HomeTabbedScreenDirections.actionHomeTabbedScreenToProfileFragment()
                    v.findNavController().navigate(directions)
                    alertDialog.hide()
                }
                alertDialog.show()
                Log.w(TAG, "Patient is null")
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