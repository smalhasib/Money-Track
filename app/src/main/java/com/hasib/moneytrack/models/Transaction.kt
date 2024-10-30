package com.hasib.moneytrack.models

import java.time.LocalDateTime

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER
}

sealed class Transaction {
    abstract val userId: String
    abstract val amount: Double
    abstract val type: TransactionType
    abstract val note: String
    abstract val dateTime: LocalDateTime
    abstract val createdAt: LocalDateTime
    abstract val updatedAt: LocalDateTime
}

data class Expense(
    override val userId: String,
    override val amount: Double,
    val category: Category,
    val account: Account,
    override val type: TransactionType = TransactionType.EXPENSE,
    override val note: String = "",
    override val dateTime: LocalDateTime,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime = createdAt
) : Transaction()

data class Income(
    override val userId: String,
    override val amount: Double,
    val category: Category,
    val account: Account,
    override val type: TransactionType = TransactionType.INCOME,
    override val note: String = "",
    override val dateTime: LocalDateTime,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime = createdAt
) : Transaction()

data class Transfer(
    override val userId: String,
    override val amount: Double,
    val fromAccount: Account,
    val toAccount: Account,
    override val type: TransactionType = TransactionType.TRANSFER,
    override val note: String = "",
    override val dateTime: LocalDateTime,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime = createdAt
) : Transaction()
