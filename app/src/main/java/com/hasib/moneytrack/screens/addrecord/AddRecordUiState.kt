package com.hasib.moneytrack.screens.addrecord

import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
import com.hasib.moneytrack.models.TransactionType
import java.time.LocalDateTime

data class AddRecordUiState(
    val expression: String = "",
    val result: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val fromAccount: Account,
    val category: Category? = null,
    val toAccount: Account? = null,
    val dateTime: LocalDateTime = LocalDateTime.now()
)