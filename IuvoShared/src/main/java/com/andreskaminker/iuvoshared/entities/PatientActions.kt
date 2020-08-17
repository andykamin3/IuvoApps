package com.andreskaminker.iuvoshared.entities

abstract class PatientActions(id: String)

enum class ActionKind(val value: Int) {
    ACTION(0), MESSAGE(1), MEDICATION(2), APPOINTMENT(3)
}