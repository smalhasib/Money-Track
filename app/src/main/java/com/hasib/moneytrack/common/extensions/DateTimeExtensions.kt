package com.hasib.moneytrack.common.extensions

import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun Long.toLocalDate(): LocalDate = ZonedDateTime.from(Instant.ofEpochMilli(this)).toLocalDate()

fun LocalDateTime.toTimestamp() = Timestamp(atZone(ZoneId.systemDefault()).toEpochSecond(), nano)

fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone)
