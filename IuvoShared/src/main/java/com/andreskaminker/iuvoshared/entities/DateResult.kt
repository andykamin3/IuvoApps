package com.andreskaminker.iuvoshared.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.andreskaminker.iuvoshared.helpers.*

@Entity(tableName = "date_results")
class DateResult {
    @PrimaryKey(autoGenerate = true)
    var roomInt = 0
    @ColumnInfo(name = "minutes")
    var mMinutes: Int? = null
    @Ignore
    var mSeconds: Int? = 0

    @ColumnInfo(name = "hour")
    var mHour: Int? = null

    @ColumnInfo(name = "day")
    var mDay: Int? = null

    @ColumnInfo(name = "month")
    var mMonth: Int? = null

    @ColumnInfo(name = "year")
    var mYear: Int? = null

    fun retrieveLocalDate(): org.threeten.bp.LocalDate? {
        return org.threeten.bp.LocalDate.of(
            this.mYear!!,
            this.mMonth?.let { mapToABPMonth(it) }!!,
            this.mDay!!
        )
    }
}