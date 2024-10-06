package com.hasib.moneytrack.helpers.extensions

fun Char.isNumber(): Boolean {
    return (isDigit())
}

fun Char.isOperator(): Boolean {
    return (isUnaryOperator() || isBinaryOperator())
}

fun Char.isUnaryOperator(): Boolean {
    return (isRightUnaryOperator())
}

fun Char.isRightUnaryOperator(): Boolean {
    val charSet = setOf('%')
    return when (this) {
        in charSet -> true
        else -> false
    }
}

fun Char.isBinaryOperator(): Boolean {
    // \u00D7 = × (multiplication)
    // \u2212 = − (minus)
    // \u002B = + (addition)
    // \u00F7 = ÷  (division)
    val charSet = setOf('^', '-', '\u2212', '+', '\u002B', 'x', '*', '\u00D7', '/', '\u00F7')
    return when (this) {
        in charSet -> true
        else -> false
    }
}
