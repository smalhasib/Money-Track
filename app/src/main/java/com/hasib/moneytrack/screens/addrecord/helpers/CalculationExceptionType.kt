package com.hasib.moneytrack.screens.addrecord.helpers

enum class CalculationExceptionType {
    INVALID_EXPRESSION,
    DIVIDE_BY_ZERO,
    VALUE_TOO_LARGE,
    DOMAIN_ERROR
}

class CalculationException(
    val msg: CalculationExceptionType
) : ArithmeticException(msg.name)