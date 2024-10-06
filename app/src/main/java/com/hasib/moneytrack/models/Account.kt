package com.hasib.moneytrack.models

data class Account(
    val name: String,
    val initialBalance: Double,
    val currentBalance: Double
) {
    constructor(name: String, initialBalance: Double) : this(name, initialBalance, initialBalance)
}
