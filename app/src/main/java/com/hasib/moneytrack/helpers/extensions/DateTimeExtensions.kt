package com.hasib.moneytrack.helpers.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime

fun Long.toLocalDate(): LocalDate {
    return ZonedDateTime.from(Instant.ofEpochMilli(this)).toLocalDate()
}
