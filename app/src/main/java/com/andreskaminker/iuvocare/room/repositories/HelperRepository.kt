package com.andreskaminker.iuvocare.room.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HelperRepository {
    val user: FirebaseUser? = FirebaseAuth.getInstance()?.currentUser

    val helperReference = user?.uid?.let { Firebase.firestore.collection("helpers").document(it) }
}