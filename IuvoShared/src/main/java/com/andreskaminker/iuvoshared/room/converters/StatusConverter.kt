package com.andreskaminker.iuvoshared.room.converters

import androidx.room.TypeConverter
import com.andreskaminker.iuvoshared.entities.Status

class StatusConverter {
    @TypeConverter
    fun fromStatus(value: Status): Int = value.ordinal

    @TypeConverter
    fun toStatus(value: Int): Status {
        return when (value) {
            0 -> Status.ACTIVO
            1 -> Status.INACTIVO
            2 -> Status.PENDIENTE
            3 -> Status.CANCELADO
            4 -> Status.LISTO
            else -> throw Exception("Enum does not contain this value")
        }
    }
}