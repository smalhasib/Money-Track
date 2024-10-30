package com.hasib.moneytrack.screens.addrecord.helpers

import com.hasib.moneytrack.helpers.extensions.isNumber
import com.hasib.moneytrack.helpers.extensions.isOperator
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.Stack


/**
 *  prepare given expression string for calculation by replacing human readable signs with
 *  computer readable signs
 *
 *  @param[expression] = given expression string
 *  @return computer readable and calculable expression string
 */
fun prepareExpression(expression: String): String {
    if (expression.isEmpty()) return ""

    var exp = expression
    exp = removeNumberSeparator(exp)

    exp = exp.replace("÷", "/")
    exp = exp.replace("×", "*")
    exp = exp.replace("+", "+")
    exp = exp.replace("−", "-")

    exp = exp.replace("-", "+-")
    exp = exp.replace("^+-", "^-")
    exp = exp.replace("(+-", "(-")
    exp = exp.replace("*+", "*")
    exp = exp.replace("/+", "/")
    exp = exp.replace("++", "+")
    exp = if (!exp.contains("%*")) exp.replace("%", "%*") else exp
    exp = exp.replace("+)", ")")
    exp = exp.replace("-)", ")")
    exp = exp.replace("/)", ")")
    exp = exp.replace("*)", ")")
    exp = exp.replace(".)", ")")
    exp = exp.replace("^)", ")")

    return exp
}

/**
 * check whether the provided expression string's first brackets are balanced or not
 *
 *  @param[expression] given expression string
 *  @return true if brackets are balanced otherwise false
 */
fun isExpressionBalanced(expression: String): Boolean {
    val stack = Stack<Char>()
    for (char in expression) {
        if (char == '(') {
            stack.push(char)
        } else if (char == ')') {
            if (stack.isEmpty()) return false
            stack.pop()
        }
    }
    return stack.isEmpty()
}

/**
 * balance brackets of given expression for evaluating value
 *
 * @param[expression] given expression string
 * @return bracket balanced string
 */
fun tryBalancingBrackets(expression: String): String {
    var exp = expression
    var a = 0
    var b = 0

    if (exp.last() == '(') {
        while (exp.last() == '(') {
            exp = exp.dropLast(1)
            if (exp.isEmpty()) return exp
        }
    }

    var openBracketCount = 0
    var numOfPairs = 0

    for (element in exp) {
        if (element == '(') {
            openBracketCount++
            a++
        } else if (element == ')') {
            b++
            if (openBracketCount > 0) {
                openBracketCount--
                numOfPairs++
            }
        }
    }

    var reqOpen = b - numOfPairs
    var reqClose = a - numOfPairs
    while (reqOpen > 0) {
        exp = "($exp"
        reqOpen--
    }
    while (reqClose > 0) {
        exp = "$exp)"
        reqClose--
    }
    return exp
}

/**
 * evaluate provided expression string and return result
 *
 * @param[expression] given expression string
 * @return calculated result string
 */
fun getResult(expression: String): String {
    if (expression.isEmpty()) return ""
    var exp = expression
    var lastChar = exp.last()

    while (!canBeLastChar(lastChar)) {
        exp = exp.dropLast(1)
        if (exp.isNotEmpty()) {
            lastChar = exp.last()
        } else {
            throw CalculationException(CalculationExceptionType.INVALID_EXPRESSION)
        }
    }

    if (exp[0] == '+' || exp[0] == '-') {
        exp = "0$exp"
    }

    val stack = Stack<String>()
    val temp = StringBuilder()
    for (i in exp.indices) {
        val char = exp[i]
        if (char.isOperator() || char == '(') {
            if (temp.isNotEmpty()) {
                stack.push(temp.toString())
                temp.clear()
            }
            stack.push(char.toString())
        } else if (char == ')') {
            if (temp.isNotEmpty()) {
                stack.push(temp.toString())
                temp.clear()
            }
            val newStack = Stack<String>()
            while (!stack.empty() && stack.peek() != "(") {
                newStack.push(stack.pop())
            }
            stack.pop()
            stack.push(solveExpression(newStack))
        } else {
            temp.append(char)
        }
    }
    if (temp.isNotEmpty()) {
        stack.push(temp.toString())
    }
    stack.reverse()

    return solveExpression(stack)
}

/**
 * solve expression/equation on basis of individual mathematical signs
 *
 * @param[expressionStack] stack of expression/equations to be calculated
 * @return solved answer string
 */
fun solveExpression(expressionStack: Stack<String>): String {
    var stack: Stack<String>

    if (expressionStack.contains("-")) {
        stack = Stack()
        while (expressionStack.isNotEmpty()) {
            if (expressionStack.peek() == "-") {
                expressionStack.pop()
                val negValue = "-${expressionStack.pop()}"
                stack.push(negValue)
                continue
            }
            stack.push(expressionStack.pop())
        }
        stack.reverse()
    } else {
        stack = expressionStack
    }

    // Check if solved
    if (stack.size == 1) return stack.pop()

    stack = solveUndefinedValue(stack)
    if (stack.size == 1) return stack.pop()

    stack = solveRightUnary(stack)
    if (stack.size == 1) return stack.pop()

    stack = solveDivision(stack)
    if (stack.size == 1) return stack.pop()

    stack = solveMultiplication(stack)
    if (stack.size == 1) return stack.pop()

    stack = solveAddition(stack)
    if (stack.size == 1) return stack.pop()

    throw CalculationException(CalculationExceptionType.INVALID_EXPRESSION)
}

/**
 * perform addition on provided expression string
 *
 * @param[stack] stack of provided expression/equation string
 * @return result string of addition operation
 */
fun solveAddition(stack: Stack<String>): Stack<String> {
    val tempStack = Stack<String>()
    var temp: String
    while (stack.isNotEmpty()) {
        temp = stack.pop()
        if (temp == "+") {
            val precision = MathContext(20)
            val num1 = tempStack.pop().toBigDecimal()
            val num2 = stack.pop().toBigDecimal()
            val result = num1.add(num2, precision)
            tempStack.push(result.toString())
        } else {
            tempStack.push(temp)
        }
    }
    tempStack.reverse()
    return tempStack
}

/**
 * perform multiplication on provided expression string
 *
 * @param[stack] stack of provided expression/equation string
 * @return result string of multiplication operation
 */
fun solveMultiplication(stack: Stack<String>): Stack<String> {
    val tempStack = Stack<String>()
    var temp: String
    while (stack.isNotEmpty()) {
        temp = stack.pop()
        if (temp == "*") {
            val precision = MathContext(20)
            val num1 = tempStack.pop().toBigDecimal()
            val num2 = stack.pop().toBigDecimal()
            val result = num1.multiply(num2, precision)
            tempStack.push(result.toString())
        } else {
            tempStack.push(temp)
        }
    }

    tempStack.reverse()
    return tempStack
}

/**
 * perform division on provided expression string
 *
 * @param[stack] stack of provided expression/equation string
 * @return result string of division operation
 */
fun solveDivision(stack: Stack<String>): Stack<String> {
    val tempStack = Stack<String>()
    var temp: String
    while (stack.isNotEmpty()) {
        temp = stack.pop()
        if (temp == "/") {
            val precision = MathContext(20)
            val num1 = tempStack.pop().toBigDecimal()
            val num2 = stack.pop().toBigDecimal()
            if (num2.compareTo(BigDecimal.ZERO) == 0)
                throw CalculationException(CalculationExceptionType.DIVIDE_BY_ZERO)
            val result = num1.divide(num2, precision)
            tempStack.push(result.toString())
        } else {
            tempStack.push(temp)
        }
    }
    tempStack.reverse()
    return tempStack
}

/**
 * perform percentage on provided expression string
 *
 * @param[stack] stack of provided expression/equation string
 * @return result string of percentage operation
 */
private fun solveRightUnary(stack: Stack<String>): Stack<String> {
    val tempStack = Stack<String>()
    var temp: String
    while (stack.isNotEmpty()) {
        temp = stack.pop()
        when (temp) {
            "%" -> {
                val precision = MathContext(20)
                var num = tempStack.pop().toBigDecimal()
                num = if (tempStack.size >= 2 && tempStack.peek() == "+") {
                    tempStack.pop()
                    val s = Stack<String>()
                    while (tempStack.isNotEmpty()) {
                        s.push(tempStack.pop())
                    }
                    val stepResult = solveExpression(s).toBigDecimal()
                    num = num.divide(BigDecimal.valueOf(100), precision)
                    num = num.multiply(stepResult, precision)
                    num.add(stepResult, precision)
                } else {
                    num.divide(BigDecimal.valueOf(100), precision)
                }
                tempStack.push(num.toString())
            }

            else -> {
                tempStack.push(temp)
            }
        }
    }
    tempStack.reverse()
    return tempStack
}

private fun solveUndefinedValue(
    stack: Stack<String>
): Stack<String> {

    return if (stack.contains("∞") || stack.contains("-∞")) {
        val temp = Stack<String>()
        temp.push("∞")
        temp
    } else stack
}

/**
 * round up answer with respect to precision parameter
 *
 * @param[ans] provided string
 * @param[precision] value of rounding up string
 * @return rounded up string
 */
fun roundAnswer(ans: String, precision: Int = 6): String {
    try {
        if (ans.isEmpty())
            return ""
        var num = ans.toBigDecimal()
        return if (ans.contains("E"))
            formatNumber(num, 7)
        else {
            num = num.setScale(precision, RoundingMode.HALF_UP)
            num = num.stripTrailingZeros()
            if (num.compareTo(BigDecimal.ZERO) == 0) {
                "0"
            } else {
                num.toPlainString()
            }
        }
    } catch (e: NumberFormatException) {
        throw CalculationException(CalculationExceptionType.INVALID_EXPRESSION)
    }
}

fun calculate(expression: String): String {
    val newExp = if (isExpressionBalanced(expression)) {
        prepareExpression(expression)
    } else {
        val exp = tryBalancingBrackets(expression)
        if (isExpressionBalanced(exp)) {
            prepareExpression(exp)
        } else {
            ""
        }
    }

    try {
        val rawResult = getResult(newExp)
        val formattedResult = if (rawResult.isNumber()) {
            val result = roundAnswer(rawResult, 6)
            addNumberSeparator(result)
        } else rawResult

        return formattedResult
    } catch (e: CalculationException) {
        val errorMessage = when (e.msg) {
            CalculationExceptionType.INVALID_EXPRESSION -> "Invalid Expression"
            CalculationExceptionType.DIVIDE_BY_ZERO -> "Cannot divide by 0"
            CalculationExceptionType.VALUE_TOO_LARGE -> "Value too large"
            CalculationExceptionType.DOMAIN_ERROR -> "Domain error"
        }
        return errorMessage
    } catch (e: Exception) {
        return "Invalid"
    }
}
