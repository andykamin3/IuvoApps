package com.andreskaminker.iuvoshared.entities

import androidx.room.*
import com.andreskaminker.iuvoshared.room.converters.KindConverter
import com.andreskaminker.iuvoshared.room.converters.StatusConverter
import com.andreskaminker.iuvoshared.room.converters.WeekListConverter

@Entity(tableName = "medications")
@TypeConverters(KindConverter::class, StatusConverter::class, WeekListConverter::class)
class MedicationRequest() : PatientActions("") {
    @ColumnInfo(name = "medId")
    var medId: String = ""
    @PrimaryKey
    @ColumnInfo(name = "medication_name")
    var medication: String = ""

    @ColumnInfo(name = "medication_image")
    var imageURL: String = ""

    @ColumnInfo(name = "status")
    var status: Status = Status.ACTIVO

    @ColumnInfo(name = "kind")
    var kind = ActionKind.MEDICATION

    @Embedded
    var patient: Patient =
        Patient()

    @ColumnInfo(name = "week_day")
    var scheduledFor: ArrayList<Int> = arrayListOf()

    @Embedded
    var takeTime: TimeResult =
        TimeResult()

    constructor(
        id: String,
        patient: Patient,
        medicationName: String,
        scheduledFor: ArrayList<Int>,
        imageUrl: String,
        takeTime: TimeResult
    ) : this() {
        this.medId = id
        this.medication = medicationName
        this.scheduledFor = scheduledFor
        this.imageURL = imageUrl
        this.takeTime = takeTime
        this.patient = patient
    }

    init {
        status = Status.ACTIVO
        kind = ActionKind.MEDICATION
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MedicationRequest

        if (medId != other.medId) return false
        if (medication != other.medication) return false

        return true
    }

    override fun hashCode(): Int {
        var result = medId.hashCode()
        result = 31 * result + medication.hashCode()
        return result
    }
}