package com.andreskaminker.iuvocare.room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andreskaminker.iuvocare.room.repositories.PatientRepository
import com.andreskaminker.iuvoshared.entities.Patient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientViewModel(application: Application) : AndroidViewModel(application){
    private val patientRepository: PatientRepository
    init {
        Log.d(TAG, "Initialized")
        patientRepository = PatientRepository()
        Log.d(TAG, FirebaseAuth.getInstance().currentUser?.uid)
        getPatients()

    }
    val patient: MutableLiveData<Patient> = MutableLiveData()

    private fun getPatients(): MutableLiveData<Patient>{
        patientRepository.patientReference.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                patient.value = null
                return@EventListener
            }
            patient.value = value?.documents?.get(0)?.toObject(Patient::class.java)
        })
        return patient
    }

    companion object{
       val TAG = "PatientViewModel"
    }
}