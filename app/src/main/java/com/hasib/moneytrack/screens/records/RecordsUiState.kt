package com.hasib.moneytrack.screens.records

import com.hasib.moneytrack.models.Transaction

data class RecordsUiState(
    val items: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
)