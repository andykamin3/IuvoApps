package com.andreskaminker.iuvoshared.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "helpers")
data class Helper(
    @PrimaryKey @ColumnInfo(name = "firestoreId") val id: String,
    @ColumnInfo(name = "helper_name") val name_given: String,
    @ColumnInfo(name = "helper_email") val email: String,
    @ColumnInfo(name = "helped") val helped: String
) {

    constructor() : this("", "", "", "") { //Empty constructor for Firebase deserialization purposes

    }

}
