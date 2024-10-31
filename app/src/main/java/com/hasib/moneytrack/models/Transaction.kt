package com.hasib.moneytrack.models

import com.google.firebase.Timestamp

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
    abstract val dateTime: Timestamp
    abstract val createdAt: Timestamp
    abstract val updatedAt: Timestamp

    fun copy(userId: String?): Transaction {
        return when (this) {
            is Expense -> copy(userId = userId ?: this.userId)
            is Income -> copy(userId = userId ?: this.userId)
            is Transfer -> copy(userId = userId ?: this.userId)
        }
    }
}

data class Expense(
    override val userId: String = "",
    override val amount: Double,
    val category: Category,
    val account: Account,
    override val type: TransactionType = TransactionType.EXPENSE,
    override val note: String = "",
    override val dateTime: Timestamp,
    override val createdAt: Timestamp,
    override val updatedAt: Timestamp = createdAt
) : Transaction() {
    constructor() : this(
        userId = "",
        amount = 0.0,
        category = Category(),
        account = Account(),
        dateTime = Timestamp.now(),
        createdAt = Timestamp.now()
    )
}

data class Income(
    override val userId: String = "",
    override val amount: Double,
    val category: Category,
    val account: Account,
    override val type: TransactionType = TransactionType.INCOME,
    override val note: String = "",
    override val dateTime: Timestamp,
    override val createdAt: Timestamp,
    override val updatedAt: Timestamp = createdAt
) : Transaction() {
    constructor() : this(
        userId = "",
        amount = 0.0,
        category = Category(),
        account = Account(),
        dateTime = Timestamp.now(),
        createdAt = Timestamp.now()
    )
}

data class Transfer(
    override val userId: String = "",
    override val amount: Double,
    val fromAccount: Account,
    val toAccount: Account,
    override val type: TransactionType = TransactionType.TRANSFER,
    override val note: String = "",
    override val dateTime: Timestamp,
    override val createdAt: Timestamp,
    override val updatedAt: Timestamp = createdAt
) : Transaction() {
    constructor() : this(
        userId = "",
        amount = 0.0,
        fromAccount = Account(),
        toAccount = Account(),
        dateTime = Timestamp.now(),
        createdAt = Timestamp.now()
    )
}
