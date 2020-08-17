package com.andreskaminker.iuvoshared.room.converters

import androidx.room.TypeConverter
import org.json.JSONArray

class WeekListConverter {
    @TypeConverter
    fun fromArrayList(value: ArrayList<Int>): String {
        return JSONArray(value).toString()
    }

    @TypeConverter
    fun toArrayList(value: String): ArrayList<Int> {
        val arr = JSONArray(value)
        var toReturn = arrayListOf<Int>()
        (0 until arr.length()).forEach { elem ->
            toReturn.add(arr.getInt(elem))
        }
        return toReturn
    }

}