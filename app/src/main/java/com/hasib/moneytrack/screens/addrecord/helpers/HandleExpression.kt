package com.hasib.moneytrack.screens.addrecord.helpers

import com.hasib.moneytrack.common.extensions.isBinaryOperator
import com.hasib.moneytrack.common.extensions.isNumber
import com.hasib.moneytrack.common.extensions.isOperator
import com.hasib.moneytrack.common.extensions.isRightUnaryOperator
import com.hasib.moneytrack.common.extensions.isUnaryOperator

fun makeExpression(
    expression: String,
    string: String,
    isPrevResult: Boolean = false
): String {
    val numberSet = setOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    val rightUnarySet = setOf("%")
    val binaryOperatorSet = setOf("÷", "×", "+")

    return when (string) {
        in numberSet -> handleNumberClick(expression, string, isPrevResult)
        in rightUnarySet -> handleRightUnaryClick(expression, string, isPrevResult)
        in binaryOperatorSet -> handleBinaryOperatorClick(expression, string)
        "-", "\u2212" -> handleMinusClick(expression, string) // \u2212 = − (minus)
        "( )" -> handleBracketClick(expression)
        "." -> handleDecimalClick(expression)
        "," -> handleCommaClick(expression)
        else -> "$expression$string"
    }
}

fun handleCommaClick(expression: String): String =
    if (expression.isNotEmpty()) "$expression," else ""

fun handleNumberClick(expression: String, string: String, prevResult: Boolean): String {
    if (expression.isNotEmpty()) {
        if (prevResult) return string
        val lastChar = expression.last()
        if (lastChar == ')')
            return "$expression\u00D7$string" // \u00D7 = × (multiplication)
    }
    return "$expression$string"
}

private fun handleRightUnaryClick(
    expression: String,
    string: String,
    isPrevResult: Boolean
): String {
    if (expression.isEmpty()) return ""
    if (isPrevResult) return "$expression$string"
    val lastChar = expression.last()
    if (lastChar.isNumber() || lastChar == ')') return "$expression$string"
    if (lastChar.isRightUnaryOperator()) return "${expression.dropLast(1)}$string"
    if (lastChar == '.') return "${expression}0$string"
    if (lastChar.isOperator() && expression.length > 1) {
        if (lastChar == '\u2212' || lastChar == '-') { // \u2212 = − (minus)
            val secondLast = expression[expression.lastIndex - 1]
            if (secondLast == '(')
                return expression
        }
        val exp = removeFromEndUntil(expression) { it.isOperator() }
        if (exp.isNotEmpty())
            return "$exp$string"
    }
    return expression
}

private fun handleBinaryOperatorClick(expression: String, string: String): String {
    val temp = when (string) {
        "xʸ" -> "^"
        "x\u00B2" -> "^2" // u00B2 = ² (superscript two)
        else -> string
    }
    if (expression.isEmpty()) return expression
    val lastChar = expression.last()
    if (lastChar.isNumber() || lastChar.isRightUnaryOperator() || lastChar == ')') return "$expression$temp"
    if (lastChar == '.') return "${expression}0$temp"
    if (lastChar.isOperator() && expression.length > 1) {
        if (lastChar == '\u2212' || lastChar == '-') { // \u2212 = − (minus)
            val secondLast = expression[expression.lastIndex - 1]
            if (secondLast == '(')
                return expression
        }
        val exp = removeFromEndUntil(expression) {
            it.isBinaryOperator()
        }
        return "$exp$temp"
    }
    return expression
}

private fun handleMinusClick(expression: String, string: String): String {
    if (expression.isEmpty()) return string
    val lastChar = expression.last()
    if (lastChar.isNumber() || lastChar.isUnaryOperator() || lastChar == ')' || lastChar == '(')
        return "$expression$string"
    if (lastChar == '.') return "${expression}0$string"
    if (lastChar.isBinaryOperator() && expression.length > 1 && expression[expression.lastIndex - 1].isNumber()) {
        if (lastChar == '\u2212' || lastChar == '-') { // \u2212 = − (minus)
            val exp = expression.dropLast(1)
            return "$exp+"
        }
        return "$expression$string"

    }
    return expression
}

private fun handleBracketClick(expression: String): String {
    if (expression.isEmpty()) return "("
    val lastChar = expression.last()
    val balance = isExpressionBalanced(expression)
    if (lastChar == '(') return "$expression("
    if ((lastChar.isNumber() || lastChar.isRightUnaryOperator()) && balance) return "${expression}×("
    if ((lastChar.isNumber() || lastChar.isRightUnaryOperator()) && !balance) return "$expression)"
    if (lastChar == '.') return if (balance) "${expression}0×(" else "${expression}0)"
    if (lastChar.isOperator() && !lastChar.isRightUnaryOperator()) return "$expression("
    if (balance) return "$expression×("
    return expression
}

private fun handleDecimalClick(expression: String): String {
    if (expression.isEmpty()) return "0."
    val lastChar = expression.last()
    if (lastChar.isDigit() && canPlaceDecimal(expression)) return "$expression."
    if (lastChar.isRightUnaryOperator() || lastChar == ')') return "$expression×0."
    if (lastChar.isOperator() || lastChar == '(') return "${expression}0."
    return expression
}

fun handleDelete(expression: String): String {
    return expression.dropLast(1)
}
