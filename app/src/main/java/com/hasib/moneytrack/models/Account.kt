package com.hasib.moneytrack.models

data class Account(
    val name: String,
    val initialBalance: Double,
    val currentBalance: Double = initialBalance,
    val iconIndex: Int = 0
)
