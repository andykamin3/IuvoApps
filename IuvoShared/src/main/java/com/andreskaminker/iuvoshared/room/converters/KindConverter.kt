package com.andreskaminker.iuvoshared.room.converters

import androidx.room.TypeConverter
import com.andreskaminker.iuvoshared.entities.ActionKind

class KindConverter {
    @TypeConverter
    fun fromKind(value: ActionKind): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toHealth(value: Int): ActionKind {
        return when (value) {
            0 -> ActionKind.ACTION
            1 -> ActionKind.MESSAGE
            2 -> ActionKind.MEDICATION
            3 -> ActionKind.APPOINTMENT
            else -> throw Exception("There are no more elements in the enum")
        }
    }
}