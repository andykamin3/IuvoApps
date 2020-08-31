package com.andreskaminker.iuvocare.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvocare.room.IuvoRoomDatabase
import com.andreskaminker.iuvocare.room.repositories.AppointmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppointmentRepository
    val allAppointments: LiveData<List<Appointment>>
    val liveData: MutableLiveData<List<Appointment>>
    private lateinit var appt: MutableList<Appointment>
    init {
        val appointmentDao =
            IuvoRoomDatabase.getDatabase(application, viewModelScope).appointmentDao()
        repository = AppointmentRepository(appointmentDao)
        allAppointments = repository.allAppointments
        liveData = MutableLiveData<List<Appointment>>()
        viewModelScope.launch {
            appt=repository.getAllAppointmentsFS()
            liveData.value = appt
        }

    }

    fun refreshAppointments(newAppointments: MutableList<Appointment>)= viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
        for(appointment in newAppointments){
            repository.addAppointment(appointment)
        }
    }
    fun addAppointment(appointment: Appointment) = viewModelScope.launch(Dispatchers.IO) {
        repository.addAppointment(appointment)
    }

    fun deleteAppointment(appointment: Appointment) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAppointment(appointment)
    }
}