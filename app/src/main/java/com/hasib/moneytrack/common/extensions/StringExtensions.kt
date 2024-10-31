package com.hasib.moneytrack.common.extensions

import java.util.regex.Pattern

fun String.isNumber(): Boolean {
    return Pattern.matches("-?\\d+(\\.\\d+(E\\d+)?)?", this)
}