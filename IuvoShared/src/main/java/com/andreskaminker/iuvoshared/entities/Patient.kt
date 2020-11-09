package com.andreskaminker.iuvoshared.entities

import androidx.room.Entity

@Entity(tableName = "patients")
data class Patient(
    val patId: String,
    val nameGiven: String,
    val email: String,
    val helper: String
) {
    constructor() : this(
        "",
        "",
        "",
        ""
    ) { //Empty constructor for Firebase deserialization purposes}
    }
}