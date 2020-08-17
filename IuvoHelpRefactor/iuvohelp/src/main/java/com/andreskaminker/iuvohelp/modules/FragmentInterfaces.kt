package com.andreskaminker.iuvohelp.modules


import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.entities.MedicationRequest

interface AppointmentFragmentFunctions {
    fun deleteAppointment(appointment: Appointment)
}

interface MedicationFragmentFunctions {
    fun deleteMedication(medicationRequest: MedicationRequest)
}