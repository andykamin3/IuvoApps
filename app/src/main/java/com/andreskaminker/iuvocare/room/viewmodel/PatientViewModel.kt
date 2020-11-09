package com.andreskaminker.iuvocare.room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.andreskaminker.iuvocare.room.repositories.PatientRepository
import com.andreskaminker.iuvoshared.entities.Patient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class PatientViewModel(application: Application) : AndroidViewModel(application){
    private val patientRepository: PatientRepository
    init {
        Log.d(TAG, "Initialized")
        patientRepository = PatientRepository()
        Log.d(TAG, FirebaseAuth.getInstance().currentUser?.uid)
        retrieveCurrentPatient()
        retrievePatientList()


    }
    val patient: MutableLiveData<Patient> = MutableLiveData()
    val patientList: MutableLiveData<List<Patient?>> = MutableLiveData()
    private fun retrieveCurrentPatient(): MutableLiveData<Patient>{
        patientRepository.patientReference.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                patient.value = null
                return@EventListener
            }
            if(!value!!.isEmpty){
                patient.value = value.documents[0].toObject(Patient::class.java)
                Log.d(TAG, patient.value.toString())
            } else{
                Log.d(TAG,"Patient is null in viewmodel")
                patient.value = null
            }
        })
        return patient
    }

    private fun retrievePatientList(): MutableLiveData<List<Patient?>> {
        patientRepository.patientReference.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                patientList.value = null
                return@EventListener
            }
            if(!value!!.isEmpty){
                patientList.value = value.toObjects(Patient::class.java)
                Log.d(TAG, patient.value.toString())
            } else{
                Log.d(TAG,"Patient is null in viewmodel")
                patientList.value = null
            }
        })
        return patientList
    }




    companion object{
       private val TAG = "PatientViewModel"
    }
}