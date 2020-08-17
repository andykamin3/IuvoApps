package com.andreskaminker.iuvohelp.room.repositories

import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvohelp.room.daos.AppointmentDao

class AppointmentRepository(val appointmentDao: AppointmentDao) {
    val allAppointments = appointmentDao.getAllAppointments()

    suspend fun addAppointment(appointment: Appointment) {
        appointmentDao.addAppointment(appointment)
    }


    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.deleteAppointment(appointment)
    }
}