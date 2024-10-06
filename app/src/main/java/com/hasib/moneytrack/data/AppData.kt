package com.hasib.moneytrack.data

import com.hasib.moneytrack.R
import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
import com.hasib.moneytrack.models.CategoryType
import com.hasib.moneytrack.models.Expense
import com.hasib.moneytrack.models.Income
import com.hasib.moneytrack.models.Transfer
import java.time.LocalDateTime

object AppData {
    val accounts = listOf(
        Account(name = "Cash", initialBalance = 1000.0, iconIndex = 0),
        Account(name = "Bank", initialBalance = 5000.0, iconIndex = 2),
        Account(name = "Credit Card", initialBalance = 2000.0, iconIndex = 5),
    )

    val incomeCategories = listOf(
        Category("Salary", CategoryType.INCOME),
        Category("Sale", CategoryType.INCOME),
        Category("Rental", CategoryType.INCOME),
        Category("Awards", CategoryType.INCOME),
        Category("Coupons", CategoryType.INCOME),
        Category("Grants", CategoryType.INCOME),
        Category("Lottery", CategoryType.INCOME),
        Category("Refunds", CategoryType.INCOME),
    )

    val expenseCategories = listOf(
        Category("Food", CategoryType.EXPENSE),
        Category("Transport", CategoryType.EXPENSE),
        Category("Shopping", CategoryType.EXPENSE),
        Category("Rent", CategoryType.EXPENSE),
        Category("Bills", CategoryType.EXPENSE),
        Category("Health", CategoryType.EXPENSE),
        Category("Entertainment", CategoryType.EXPENSE),
        Category("Education", CategoryType.EXPENSE),
        Category("Gifts", CategoryType.EXPENSE),
        Category("Investment", CategoryType.EXPENSE),
        Category("Telephone", CategoryType.EXPENSE),
        Category("Tax", CategoryType.EXPENSE),
        Category("Sport", CategoryType.EXPENSE),
        Category("Social", CategoryType.EXPENSE),
        Category("Insurance", CategoryType.EXPENSE),
        Category("Electronics", CategoryType.EXPENSE),
        Category("Clothing", CategoryType.EXPENSE),
        Category("Car", CategoryType.EXPENSE),
        Category("Beauty", CategoryType.EXPENSE),
        Category("Baby", CategoryType.EXPENSE),
        Category("Others", CategoryType.EXPENSE),
    )

    val transactions = listOf(
        Expense(
            amount = 45.00,
            category = incomeCategories.first { it.name == "Salary" },
            account = accounts.first { it.name == "Bank" },
            note = "Shirt",
            dateTime = LocalDateTime.of(2024, 10, 5, 11, 3),
            createdAt = LocalDateTime.now(),
        ),
        Transfer(
            amount = 500.0,
            fromAccount = accounts.first { it.name == "Bank" },
            toAccount = accounts.first { it.name == "Cash" },
            dateTime = LocalDateTime.of(2024, 10, 2, 20, 42),
            createdAt = LocalDateTime.now(),
        ),
        Income(
            amount = 458.00,
            category = incomeCategories.first { it.name == "Salary" },
            account = accounts.first { it.name == "Bank" },
            dateTime = LocalDateTime.of(2024, 10, 2, 20, 37),
            createdAt = LocalDateTime.now(),
        ),
    )

    val accountIcons = listOf(
        R.drawable.account_cash,
        R.drawable.account_card,
        R.drawable.account_bank,
        R.drawable.account_wallet,
        R.drawable.account_savings,
        R.drawable.account_credit_card,
        R.drawable.account_bkash,
        R.drawable.account_paypal,
        R.drawable.account_visa_card,
        R.drawable.account_mastercard,
        R.drawable.account_amex,
        R.drawable.account_industry,
        R.drawable.account_coins,
        R.drawable.account_store,
        R.drawable.account_expenses_sheet,
        R.drawable.account_expenses,
        R.drawable.account_mobile_wallet,
        R.drawable.account_safe_box
    )
}