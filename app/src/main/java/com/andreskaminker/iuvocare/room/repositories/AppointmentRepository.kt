package com.andreskaminker.iuvocare.room.repositories

import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvocare.room.daos.AppointmentDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppointmentRepository(val appointmentDao: AppointmentDao) {
    val allAppointments = appointmentDao.getAllAppointments()
    val appointmentReference = Firebase.firestore.collection("appointments")

    suspend fun addAppointment(appointment: Appointment) {
        appointmentDao.addAppointment(appointment)
        appointmentReference.add(appointment)
    }


    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.deleteAppointment(appointment)
    }
}