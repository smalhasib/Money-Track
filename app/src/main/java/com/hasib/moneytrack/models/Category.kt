package com.hasib.moneytrack.models

enum class CategoryType {
    INCOME,
    EXPENSE
}

data class Category(
    val name: String,
    val type: CategoryType,
    val iconIndex: Int = 0
)
