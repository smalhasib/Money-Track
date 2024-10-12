package com.hasib.moneytrack.models

import androidx.annotation.DrawableRes

sealed class BasicData {
    abstract val name: String
    abstract val imageId: Int
}

data class Account(
    override val name: String,
    val initialBalance: Double,
    val currentBalance: Double = initialBalance,
    @DrawableRes
    override val imageId: Int = -1
) : BasicData()

enum class CategoryType {
    INCOME,
    EXPENSE
}

data class Category(
    override val name: String,
    val type: CategoryType,
    @DrawableRes
    override val imageId: Int = -1
) : BasicData()
