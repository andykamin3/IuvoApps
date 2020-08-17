package com.andreskaminker.iuvoshared.entities

import androidx.room.*
import com.andreskaminker.iuvoshared.room.converters.KindConverter
import com.andreskaminker.iuvoshared.room.converters.StatusConverter

/**
 * Create a new appointment
 *
 * @property aptId
 * @property description
 * @property title
 * @property patient
 * @property scheduledFor
 */
@Entity(tableName = "appointments")
@TypeConverters(KindConverter::class, StatusConverter::class)
class Appointment() : PatientActions("") {
    @ColumnInfo(name = "status")
    var status = Status.ACTIVO
    @ColumnInfo(name = "kind")
    var kind = ActionKind.APPOINTMENT
    @ColumnInfo(name = "aptId")
    var aptId: String = ""
    @ColumnInfo(name = "apppointment_description")
    var description: String = ""
    @PrimaryKey
    @ColumnInfo(name = "apppointment_name")
    var title: String = ""
    @Embedded
    var patient: Patient = Patient()
    @Embedded
    var scheduledFor: DateResult = DateResult()

    constructor(
        id: String,
        description: String,
        title: String,
        patient: Patient,
        scheduledFor: DateResult
    ) : this() {
        this.aptId = id
        this.description = description
        this.title = title
        this.patient = patient
        this.scheduledFor = scheduledFor
    }

    init {
        status = Status.ACTIVO
        kind = ActionKind.APPOINTMENT
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Appointment
        if (aptId != other.aptId) return false
        if (title != other.title) return false
        return true
    }

    override fun hashCode(): Int {
        var result = aptId.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }


}