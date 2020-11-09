package com.andreskaminker.iuvocare.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.andreskaminker.iuvocare.databinding.CardPatientsBinding
import com.andreskaminker.iuvoshared.entities.Patient

class PatientsAdapter (val fragment: Fragment) :
    RecyclerView.Adapter<PatientsAdapter.PatientHolder>() {
    var patients: List<Patient> = emptyList<Patient>()

    class PatientHolder(val binding: CardPatientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        val binding = CardPatientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientHolder(binding)
    }

    override fun getItemCount(): Int = patients.size

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        val patient = patients[position]
        holder.binding.apply {
            textViewPatientEmail.text = patient.email
            textViewPatientName.text = patient.nameGiven
        }
    }

    fun setData(newData: List<Patient>){
        patients = newData
        notifyDataSetChanged()
    }


}