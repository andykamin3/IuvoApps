package com.andreskaminker.iuvoshared.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class TimeResult() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trId")
    var roomInt = 0
    var hour: Int = 0
    var minutes: Int = 0

    constructor(hour: Int, minutes: Int) : this() {
        this.minutes = minutes
        this.hour = hour
    }
}