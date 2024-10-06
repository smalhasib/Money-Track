package com.hasib.moneytrack.models

import java.time.LocalDateTime

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER
}

sealed class Transaction(
    open val amount: Double,
    open val type: TransactionType,
    open val note: String = "",
    open val dateTime: LocalDateTime,
    open val createdAt: LocalDateTime,
    open val updatedAt: LocalDateTime,
) {
    constructor(
        amount: Double,
        type: TransactionType,
        note: String,
        date: LocalDateTime,
        createdAt: LocalDateTime,
    ) : this(
        amount,
        type,
        note,
        date,
        createdAt,
        createdAt,
    )
}

data class Expense(
    override val amount: Double,
    val category: Category,
    val account: Account,
    override val note: String = "",
    override val dateTime: LocalDateTime,
    override val createdAt: LocalDateTime
) : Transaction(
    amount,
    TransactionType.EXPENSE,
    note,
    dateTime,
    createdAt,
    createdAt
)

data class Income(
    override val amount: Double,
    val category: Category,
    val account: Account,
    override val note: String = "",
    override val dateTime: LocalDateTime,
    override val createdAt: LocalDateTime
) : Transaction(
    amount,
    TransactionType.INCOME,
    note,
    dateTime,
    createdAt,
    createdAt
)

data class Transfer(
    override val amount: Double,
    val fromAccount: Account,
    val toAccount: Account,
    override val note: String = "",
    override val dateTime: LocalDateTime,
    override val createdAt: LocalDateTime,
) : Transaction(
    amount,
    TransactionType.TRANSFER,
    note,
    dateTime,
    createdAt,
    createdAt
)
