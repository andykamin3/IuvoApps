package com.andreskaminker.iuvohelp.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andreskaminker.iuvoshared.entities.MedicationRequest

@Dao
interface MedicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMedication(vararg: MedicationRequest)

    @Query("SELECT * FROM medications")
    fun getAllMedications(): LiveData<List<MedicationRequest>>

    @Delete
    suspend fun deleteMedication(vararg: MedicationRequest)
}