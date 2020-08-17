package com.andreskaminker.iuvohelp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.andreskaminker.iuvohelp.room.daos.AppointmentDao
import com.andreskaminker.iuvohelp.room.daos.MedicationDao
import com.andreskaminker.iuvoshared.entities.Appointment
import com.andreskaminker.iuvoshared.entities.MedicationRequest
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(Appointment::class, MedicationRequest::class),
    version = 5,
    exportSchema = false
)
abstract class IuvoRoomDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    abstract fun medicationDao(): MedicationDao


    companion object {
        @Volatile
        private var INSTANCE: IuvoRoomDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): IuvoRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IuvoRoomDatabase::class.java,
                    "iuvo_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}