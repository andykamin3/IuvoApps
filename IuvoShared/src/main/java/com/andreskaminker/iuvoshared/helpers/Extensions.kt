package com.andreskaminker.iuvoshared.helpers

import ca.antonious.materialdaypicker.MaterialDayPicker
import com.andreskaminker.iuvoshared.entities.Config
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.DateTimeFormatter

fun MaterialDayPicker.Weekday.mapToABP(): DayOfWeek {
    when (this) {
        MaterialDayPicker.Weekday.MONDAY -> {
            return DayOfWeek.MONDAY
        }
        MaterialDayPicker.Weekday.TUESDAY -> {
            return DayOfWeek.TUESDAY
        }
        MaterialDayPicker.Weekday.WEDNESDAY -> {
            return DayOfWeek.WEDNESDAY
        }
        MaterialDayPicker.Weekday.THURSDAY -> {
            return DayOfWeek.THURSDAY
        }
        MaterialDayPicker.Weekday.FRIDAY -> {
            return DayOfWeek.FRIDAY
        }
        MaterialDayPicker.Weekday.SATURDAY -> {
            return DayOfWeek.SATURDAY
        }
        MaterialDayPicker.Weekday.SUNDAY -> {
            return DayOfWeek.SUNDAY
        }
        else -> {
            throw Exception("Could not map to ABP as it is not included.")
        }
    }
}

fun mapToWeekday(weekday: Int): MaterialDayPicker.Weekday {
    require(weekday < 8)
    when (weekday) {
        MaterialDayPicker.Weekday.MONDAY.ordinal -> {
            return MaterialDayPicker.Weekday.MONDAY
        }
        MaterialDayPicker.Weekday.TUESDAY.ordinal -> {
            return MaterialDayPicker.Weekday.TUESDAY
        }
        MaterialDayPicker.Weekday.WEDNESDAY.ordinal -> {
            return MaterialDayPicker.Weekday.WEDNESDAY
        }
        MaterialDayPicker.Weekday.THURSDAY.ordinal -> {
            return MaterialDayPicker.Weekday.THURSDAY
        }
        MaterialDayPicker.Weekday.FRIDAY.ordinal -> {
            return MaterialDayPicker.Weekday.FRIDAY
        }
        MaterialDayPicker.Weekday.SATURDAY.ordinal -> {
            return MaterialDayPicker.Weekday.SATURDAY
        }
        MaterialDayPicker.Weekday.SUNDAY.ordinal -> {
            return MaterialDayPicker.Weekday.SUNDAY
        }
        else -> {
            throw  IllegalArgumentException("This is not an ordinal value of MaterialDayPicker Weekday class")
        }


    }

}

fun mapToABPMonth(month: Int): org.threeten.bp.Month {
    return when (month) {
        0 -> org.threeten.bp.Month.JANUARY
        1 -> org.threeten.bp.Month.FEBRUARY
        2 -> org.threeten.bp.Month.MARCH
        3 -> org.threeten.bp.Month.APRIL
        4 -> org.threeten.bp.Month.MAY
        5 -> org.threeten.bp.Month.JUNE
        6 -> org.threeten.bp.Month.JULY
        7 -> org.threeten.bp.Month.AUGUST
        8 -> org.threeten.bp.Month.SEPTEMBER
        9 -> org.threeten.bp.Month.OCTOBER
        10 -> org.threeten.bp.Month.NOVEMBER
        11 -> org.threeten.bp.Month.DECEMBER
        else -> throw Exception("Hello")
    }
}

object FormatUtils {
    val titleSameYearFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("MMMM").withLocale(Config.default_locale)
    val titleFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("MMM yyyy").withLocale(Config.default_locale)
    val selectionFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("d MMM yyyy").withLocale(Config.default_locale)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

}
