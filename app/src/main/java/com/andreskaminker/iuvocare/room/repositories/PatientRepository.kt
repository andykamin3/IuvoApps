package com.andreskaminker.iuvocare.room.repositories

import com.andreskaminker.iuvocare.room.daos.FirestoreFunctions
import com.andreskaminker.iuvoshared.entities.Patient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PatientRepository() {
    val user: FirebaseUser? = FirebaseAuth.getInstance()?.currentUser

    val patientReference = Firebase.firestore.collection("patients").whereEqualTo("helper", user!!.uid)
}