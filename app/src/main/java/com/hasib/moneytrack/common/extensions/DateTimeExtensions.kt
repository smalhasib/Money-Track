package com.hasib.moneytrack.common.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime

fun Long.toLocalDate(): LocalDate = ZonedDateTime.from(Instant.ofEpochMilli(this)).toLocalDate()
