package com.hasib.moneytrack.screens.addrecord

import com.google.firebase.Timestamp
import com.hasib.moneytrack.base.BaseViewModel
import com.hasib.moneytrack.common.extensions.isNumber
import com.hasib.moneytrack.common.extensions.toTimestamp
import com.hasib.moneytrack.common.snackbar.SnackBarManager
import com.hasib.moneytrack.data.AppUserManager
import com.hasib.moneytrack.data.repositories.RecordsRepository
import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
import com.hasib.moneytrack.models.Expense
import com.hasib.moneytrack.models.Income
import com.hasib.moneytrack.models.TransactionType
import com.hasib.moneytrack.models.Transfer
import com.hasib.moneytrack.screens.addrecord.helpers.addNumberSeparator
import com.hasib.moneytrack.screens.addrecord.helpers.calculate
import com.hasib.moneytrack.screens.addrecord.helpers.handleDelete
import com.hasib.moneytrack.screens.addrecord.helpers.makeExpression
import com.hasib.moneytrack.screens.addrecord.helpers.removeNumberSeparator
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import com.hasib.moneytrack.R.string as AppText

@HiltViewModel
class AddRecordViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository,
    appUserManager: AppUserManager,
    logService: LogService,
    navigationService: NavigationService,
) : BaseViewModel(logService, navigationService) {

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
        _uiState.update {
            if (account == it.toAccount) {
                SnackBarManager.showMessage(AppText.same_account_error)
                return
            }

            it.copy(fromAccount = account)
        }
    }

    fun setToAccount(account: Account) {
        _uiState.update {
            if (account == it.fromAccount) {
                SnackBarManager.showMessage(AppText.same_account_error)
                return
            }

            it.copy(toAccount = account)
        }
    }

    fun setCategory(category: Category) {
        _uiState.update { it.copy(category = category) }
    }

    fun setNote(note: String) {
        _uiState.update { it.copy(note = note) }
    }

    fun setDate(date: LocalDate) {
        _uiState.update { it.copy(dateTime = LocalDateTime.of(date, it.dateTime.toLocalTime())) }
    }

    fun setTime(time: LocalTime) {
        _uiState.update { it.copy(dateTime = LocalDateTime.of(it.dateTime.toLocalDate(), time)) }
    }

    fun onCancelClick() = popUp()

    private fun showLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    fun onSaveClick() {
        if (!isValidated()) {
            return
        }

        launchCatching {
            showLoading(true)
            val transaction = _uiState.value.run {
                val amount = removeNumberSeparator(result.ifEmpty { expression }).toDouble()
                when (transactionType) {
                    TransactionType.INCOME -> {
                        Income(
                            amount = amount,
                            category = category!!,
                            account = fromAccount,
                            dateTime = dateTime.toTimestamp(),
                            createdAt = Timestamp.now()
                        )
                    }

                    TransactionType.EXPENSE -> {
                        Expense(
                            amount = amount,
                            category = category!!,
                            account = fromAccount,
                            dateTime = dateTime.toTimestamp(),
                            createdAt = Timestamp.now()
                        )
                    }

                    TransactionType.TRANSFER -> {
                        Transfer(
                            amount = amount,
                            fromAccount = fromAccount,
                            toAccount = toAccount!!,
                            dateTime = dateTime.toTimestamp(),
                            createdAt = Timestamp.now()
                        )
                    }
                }
            }
            recordsRepository.addRecord(transaction).await()
            Timber.d(_uiState.value.toString())
            showLoading(false)
            popUp()
        }
    }

    private fun isValidated(): Boolean {
        _uiState.value.run {
            return when {
                transactionType != TransactionType.TRANSFER && category == null -> {
                    SnackBarManager.showMessage(AppText.category_error)
                    false
                }

                transactionType == TransactionType.TRANSFER && toAccount == null -> {
                    SnackBarManager.showMessage(AppText.to_account_error)
                    false
                }

                expression.isEmpty() -> {
                    SnackBarManager.showMessage(AppText.amount_error)
                    false
                }

                else -> true
            }
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
}
