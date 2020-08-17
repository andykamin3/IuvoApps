package com.andreskaminker.iuvoshared.entities

import org.threeten.bp.DayOfWeek
import org.threeten.bp.temporal.WeekFields
import java.util.*


object Config {
    val default_locale = Locale("es", "AR")
    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(default_locale).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs =
                daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last) //Dark magic no tocar :))
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }
}