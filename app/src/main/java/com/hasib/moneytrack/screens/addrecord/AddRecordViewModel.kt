package com.hasib.moneytrack.screens.addrecord

import androidx.lifecycle.ViewModel
import com.hasib.moneytrack.data.AppUserManager
import com.hasib.moneytrack.helpers.extensions.isNumber
import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
import com.hasib.moneytrack.models.TransactionType
import com.hasib.moneytrack.navigation.Navigator
import com.hasib.moneytrack.screens.addrecord.helpers.CalculationException
import com.hasib.moneytrack.screens.addrecord.helpers.CalculationExceptionType
import com.hasib.moneytrack.screens.addrecord.helpers.addNumberSeparator
import com.hasib.moneytrack.screens.addrecord.helpers.getResult
import com.hasib.moneytrack.screens.addrecord.helpers.handleDelete
import com.hasib.moneytrack.screens.addrecord.helpers.isExpressionBalanced
import com.hasib.moneytrack.screens.addrecord.helpers.makeExpression
import com.hasib.moneytrack.screens.addrecord.helpers.prepareExpression
import com.hasib.moneytrack.screens.addrecord.helpers.removeNumberSeparator
import com.hasib.moneytrack.screens.addrecord.helpers.roundAnswer
import com.hasib.moneytrack.screens.addrecord.helpers.tryBalancingBrackets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AddRecordViewModel(
    appUserManager: AppUserManager,
    private val navigator: Navigator
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddRecordUiState(
            fromAccount = appUserManager.defaultAccount
        )
    )
    val uiState: StateFlow<AddRecordUiState> = _uiState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var isPreviousResult = false

    fun setTransactionType(type: TransactionType) {
        _uiState.update { it.copy(transactionType = type) }
    }

    fun setFromAccount(account: Account) {
        _uiState.update { it.copy(fromAccount = account) }
    }

    fun setToAccount(account: Account) {
        _uiState.update { it.copy(toAccount = account) }
    }

    fun setCategory(category: Category) {
        _uiState.update { it.copy(category = category) }
    }

    fun setDate(date: LocalDate) {
        _uiState.update { it.copy(dateTime = LocalDateTime.of(date, it.dateTime.toLocalTime())) }
    }

    fun setTime(time: LocalTime) {
        _uiState.update { it.copy(dateTime = LocalDateTime.of(it.dateTime.toLocalDate(), time)) }
    }

    fun saveData(): Boolean {
        if (!validated()) {
            return false
        }
        Timber.d(_uiState.value.toString())
        return true
    }

    private fun validated(): Boolean {
        return when {
            _uiState.value.category == null -> {
                _error.value = "Please select a category"
                false
            }

            _uiState.value.expression.isEmpty() -> {
                _error.value = "Please enter a valid amount"
                false
            }

            else -> true
        }
    }

    fun handleClick(label: String) {
        _uiState.update { state ->
            val expression = removeNumberSeparator(state.expression.trim())

            if (label in listOf("bs", "AC", "=")) {
                val result = state.result.trim()

                if (expression.isNotEmpty()) {
                    when (label) {
                        "bs" -> {
                            val newExpression = handleDelete(expression)
                            state.copy(
                                expression = addNumberSeparator(newExpression),
                                result = when {
                                    newExpression.isEmpty() -> ""
                                    newExpression.isNumber() -> calculate(newExpression)
                                    else -> result
                                }
                            )
                        }

                        "AC" -> state.copy(expression = "", result = "")
                        "=" -> {
                            if (result.isEmpty() || !removeNumberSeparator(result).isNumber()) {
                                state.copy(result = "")
                            } else {
                                isPreviousResult = true
                                state.copy(expression = result, result = "")
                            }
                        }

                        else -> state
                    }
                } else state
            } else {
                var newExpression = makeExpression(expression, label, isPreviousResult)
                isPreviousResult = false
                newExpression = addNumberSeparator(newExpression)
                state.copy(expression = newExpression, result = calculate(newExpression))
            }
        }
    }

    private fun calculate(expression: String): String {
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
}

data class AddRecordUiState(
    val expression: String = "",
    val result: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val fromAccount: Account,
    val category: Category? = null,
    val toAccount: Account? = null,
    val dateTime: LocalDateTime = LocalDateTime.now()
)