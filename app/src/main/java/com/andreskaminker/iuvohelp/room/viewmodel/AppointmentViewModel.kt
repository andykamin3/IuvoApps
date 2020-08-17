package com.andreskaminker.iuvohelp.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvohelp.room.IuvoRoomDatabase
import com.andreskaminker.iuvohelp.room.repositories.AppointmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppointmentRepository
    val allAppointments: LiveData<List<Appointment>>

    init {
        val appointmentDao =
            IuvoRoomDatabase.getDatabase(application, viewModelScope).appointmentDao()
        repository = AppointmentRepository(appointmentDao)
        allAppointments = repository.allAppointments
    }

    fun addAppointment(appointment: Appointment) = viewModelScope.launch(Dispatchers.IO) {
        repository.addAppointment(appointment)
    }

    fun deleteAppointment(appointment: Appointment) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAppointment(appointment)
    }
}