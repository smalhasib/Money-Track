package com.hasib.moneytrack.screens.add_record.helpers

import com.hasib.moneytrack.helpers.extensions.isNumber
import com.hasib.moneytrack.helpers.extensions.isOperator
import com.hasib.moneytrack.helpers.extensions.isRightUnaryOperator
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun removeNumberSeparator(expression: String, separator: Char = ','): String {
    return expression.replace(separator.toString(), "")
}

fun addNumberSeparator(
    expression: String,
    separator: Char = ',',
): String {
    if (expression.contains("E"))
        return expression
    val expressionList = separateOutExpression(expression).map {
        if (it.isNumber()) {
            addSeparator(it, separator)
        } else {
            it
        }
    }
    val stringBuilder = StringBuilder()
    expressionList.forEach { stringBuilder.append(it) }
    return stringBuilder.toString()
}

fun separateOutExpression(expression: String): List<String> {
    val list = mutableListOf<String>()
    val temp = StringBuilder()
    for (i in expression.indices) {
        val char = expression[i]
        if (char.isOperator() || char == '(' || char == ')') {
            if (temp.isNotEmpty()) {
                list.add(temp.toString())
                temp.clear()
            }
            list.add(char.toString())
        } else {
            temp.append(char)
        }
    }
    if (temp.isNotEmpty()) {
        list.add(temp.toString())
    }
    return list
}

private fun addSeparator(string: String, separator: Char): String {
    var decimalIndex = string.indexOf('.')
    if (decimalIndex == -1)
        decimalIndex = string.length
    var str = string
    var temp = 0
    for (i in decimalIndex - 1 downTo 1) {
        temp++
        if (temp % 3 == 0) {
            temp = 0
            if (i == 1 && (string[0] == '-' || string[0] == '\u2212')) break // \u2212 = − (minus)
            str = str.substring(0, i) + separator + str.substring(i)
            continue
        }
    }
    return str
}

fun removeFromEndUntil(expression: String, condition: (Char) -> Boolean): String {
    if (expression.isNotEmpty()) {
        var exp = expression
        var lastChar = exp.last()
        while (condition(lastChar)) {
            exp = exp.dropLast(1)
            if (exp.isEmpty()) return ""
            lastChar = exp.last()
        }
        return exp
    }
    return ""
}

fun canPlaceDecimal(expression: String): Boolean {
    var j = expression.lastIndex
    var count = 0
    while (j >= 0 && !expression[j].isOperator()) {
        if (expression[j] == '.') {
            count++
            break
        }
        j--
    }
    return count == 0
}


//checks if the char can be a last character in a valid equation
fun canBeLastChar(char: Char): Boolean {
    return (char.isNumber() || char.isRightUnaryOperator() || char == ')')
}

fun formatNumber(x: BigDecimal, scale: Int): String {
    val formatter = DecimalFormat("0.0E0")
    formatter.roundingMode = RoundingMode.HALF_UP
    formatter.minimumFractionDigits = scale
    formatter.minimumIntegerDigits = 1
    return formatter.format(x)
}