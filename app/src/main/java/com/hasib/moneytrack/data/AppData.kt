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
        Account(name = "Cash", initialBalance = 1000.0, imageId = R.drawable.account_cash),
        Account(name = "Bank", initialBalance = 5000.0, imageId = R.drawable.account_bank),
        Account(
            name = "Credit Card",
            initialBalance = 2000.0,
            imageId = R.drawable.account_credit_card
        ),
    )

    val incomeCategories = listOf(
        Category("Salary", CategoryType.INCOME, R.drawable.income_salary),
        Category("Rental", CategoryType.INCOME, R.drawable.income_rental),
        Category("Awards", CategoryType.INCOME, R.drawable.income_awards),
        Category("Coupons", CategoryType.INCOME, R.drawable.income_coupons),
        Category("Grants", CategoryType.INCOME, R.drawable.income_grants),
        Category("Lottery", CategoryType.INCOME, R.drawable.income_lottery),
        Category("Refunds", CategoryType.INCOME, R.drawable.income_refunds),
        Category("Sale", CategoryType.INCOME, R.drawable.income_sale),
    )

    val expenseCategories = listOf(
        Category("Food", CategoryType.EXPENSE, R.drawable.expense_food),
        Category("Transportation", CategoryType.EXPENSE, R.drawable.expense_transportation),
        Category("Shopping", CategoryType.EXPENSE, R.drawable.expense_shopping),
        Category("Rent", CategoryType.EXPENSE, R.drawable.expense_rent),
        Category("Bill", CategoryType.EXPENSE, R.drawable.expense_bill),
        Category("Healthcare", CategoryType.EXPENSE, R.drawable.expense_healthcare),
        Category("Entertainment", CategoryType.EXPENSE, R.drawable.expense_entertainment),
        Category("Education", CategoryType.EXPENSE, R.drawable.expense_education),
        Category("Gifts", CategoryType.EXPENSE, R.drawable.expense_gifts),
        Category("Investment", CategoryType.EXPENSE, R.drawable.expense_investment),
        Category("Telephone", CategoryType.EXPENSE, R.drawable.expense_telephone),
        Category("Tax", CategoryType.EXPENSE, R.drawable.expense_tax),
        Category("Sports", CategoryType.EXPENSE, R.drawable.expense_sports),
        Category("Social", CategoryType.EXPENSE, R.drawable.expense_social),
        Category("Insurance", CategoryType.EXPENSE, R.drawable.expense_insurance),
        Category("Electronics", CategoryType.EXPENSE, R.drawable.expense_electronics),
        Category("Clothing", CategoryType.EXPENSE, R.drawable.expense_clothing),
        Category("Car", CategoryType.EXPENSE, R.drawable.expense_car),
        Category("Beauty", CategoryType.EXPENSE, R.drawable.expense_beauty),
        Category("Baby", CategoryType.EXPENSE, R.drawable.expense_baby),
        Category("Others", CategoryType.EXPENSE, R.drawable.expense_others),
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
}