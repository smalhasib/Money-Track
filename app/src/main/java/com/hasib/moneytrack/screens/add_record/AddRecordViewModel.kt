package com.hasib.moneytrack.screens.add_record

import androidx.lifecycle.ViewModel
import com.hasib.moneytrack.data.AppUserManager
import com.hasib.moneytrack.helpers.extensions.isNumber
import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
import com.hasib.moneytrack.models.TransactionType
import com.hasib.moneytrack.screens.add_record.helpers.CalculationException
import com.hasib.moneytrack.screens.add_record.helpers.CalculationExceptionType
import com.hasib.moneytrack.screens.add_record.helpers.addNumberSeparator
import com.hasib.moneytrack.screens.add_record.helpers.getResult
import com.hasib.moneytrack.screens.add_record.helpers.handleDelete
import com.hasib.moneytrack.screens.add_record.helpers.isExpressionBalanced
import com.hasib.moneytrack.screens.add_record.helpers.makeExpression
import com.hasib.moneytrack.screens.add_record.helpers.prepareExpression
import com.hasib.moneytrack.screens.add_record.helpers.removeNumberSeparator
import com.hasib.moneytrack.screens.add_record.helpers.roundAnswer
import com.hasib.moneytrack.screens.add_record.helpers.tryBalancingBrackets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddRecordViewModel @Inject constructor(
    appUserManager: AppUserManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddRecordUiState(
            fromAccount = appUserManager.defaultAccount
        )
    )
    val uiState: StateFlow<AddRecordUiState> = _uiState.asStateFlow()

    private var isPreviousResult = false

    fun setTransactionType(type: TransactionType) {
        _uiState.update { it.copy(transactionType = type) }
    }

    fun setFromAccount(account: Account) {
        _uiState.update { it.copy(fromAccount = account) }
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
)