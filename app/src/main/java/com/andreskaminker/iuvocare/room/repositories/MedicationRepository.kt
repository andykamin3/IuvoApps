package com.andreskaminker.iuvocare.room.repositories

import android.util.Log
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvocare.room.daos.MedicationDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MedicationRepository(val medicationDao: MedicationDao) {
    val allMedication = medicationDao.getAllMedications()
    val medicationReference = Firebase.firestore.collection("medications")
    suspend fun addMedication(medication: MedicationRequest) {
        medicationDao.addMedication(medication)
        medicationReference.add(medication).addOnSuccessListener {
            it.set(
                mapOf(
                    "medId" to it.id
                )
            )
        }.addOnFailureListener{
            Log.w(TAG, "Failed to update doc")
        }
    }

    suspend fun deleteMedication(medication: MedicationRequest) {
        medicationDao.deleteMedication(medication)
    }
    companion object{
        const val TAG = "MedRepo"
    }
}