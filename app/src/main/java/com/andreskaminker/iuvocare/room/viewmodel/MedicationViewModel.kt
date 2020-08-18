package com.andreskaminker.iuvocare.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import com.andreskaminker.iuvocare.room.IuvoRoomDatabase
import com.andreskaminker.iuvocare.room.repositories.MedicationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MedicationRepository
    val allMedications: LiveData<List<MedicationRequest>>

    init {
        val medicationDao =
            IuvoRoomDatabase.getDatabase(application, viewModelScope).medicationDao()
        repository = MedicationRepository(medicationDao)
        allMedications = repository.allMedication
    }

    fun addMedication(medicationRequest: MedicationRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMedication(medicationRequest)
        }

    fun deleteMedication(medicationRequest: MedicationRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMedication(medicationRequest)
        }
}