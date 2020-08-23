package com.andreskaminker.iuvocare.room.daos

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirestoreFunctions {
    suspend fun getDataFromFireStore(collectionName: String, childName : String)
            : DocumentSnapshot?{
        return try{
            val data = Firebase.firestore
                .collection(collectionName)
                .document(childName)
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }
}