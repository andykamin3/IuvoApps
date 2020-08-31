package com.andreskaminker.iuvocare.room.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvocare.room.daos.AppointmentDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AppointmentRepository(private val appointmentDao: AppointmentDao) {
    val allAppointments = appointmentDao.getAllAppointments()
    val appointmentReference = Firebase.firestore.collection("appointments")


    suspend fun getAllAppointmentsFS(): MutableList<Appointment> {
        val toReturn =  appointmentReference.whereEqualTo("patient.patId","SlwL46raipRpAVTuqUNCcnqT7ON2")
            .get()
            .await()
            .toObjects(Appointment::class.java)
        Log.d(TAG, toReturn.toString())
        return  toReturn
    }

    suspend fun addAppointment(appointment: Appointment) {
        appointmentDao.addAppointment(appointment)
        appointmentReference.add(appointment)
    }

    suspend fun deleteAll(){
        appointmentDao.deleteAll()
    }


    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.deleteAppointment(appointment)
    }
    companion object{
        const val TAG = "AppointmentRepository"
    }
}