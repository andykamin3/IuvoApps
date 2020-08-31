package com.andreskaminker.iuvocare.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andreskaminker.iuvoshared.entities.Appointment

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppointment(vararg: Appointment)

    @Query("SELECT * FROM appointments")
    fun getAllAppointments(): LiveData<List<Appointment>>

    @Delete
    suspend fun deleteAppointment(vararg: Appointment)

    @Query("DELETE FROM appointments")
    suspend fun deleteAll()
}