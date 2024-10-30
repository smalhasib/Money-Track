package com.hasib.moneytrack.screens.addrecord

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
import com.hasib.moneytrack.models.CategoryType
import com.hasib.moneytrack.models.TransactionType
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AddRecordScreen(
    viewModel: AddRecordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AddRecordScreenContent(
        transactionType = uiState.transactionType,
        fromAccount = uiState.fromAccount,
        toAccount = uiState.toAccount,
        category = uiState.category,
        expression = uiState.expression,
        result = uiState.result,
        onTransactionTypeChange = viewModel::setTransactionType,
        onFromAccountChange = viewModel::setFromAccount,
        onToAccountChange = viewModel::setToAccount,
        onCategoryChange = viewModel::setCategory,
        onPadClick = viewModel::handleClick,
        onDateChange = viewModel::setDate,
        onTimeChange = viewModel::setTime,
        onCancelClick = viewModel::onCancelClick,
        onSaveClick = viewModel::onSaveClick,
    )
}

@Composable
private fun AddRecordScreenContent(
    transactionType: TransactionType,
    fromAccount: Account,
    toAccount: Account?,
    category: Category?,
    expression: String,
    result: String,
    onTransactionTypeChange: (TransactionType) -> Unit,
    onFromAccountChange: (Account) -> Unit,
    onToAccountChange: (Account) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onPadClick: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    var note by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            AddRecordAppBar(
                cancelAction = onCancelClick,
                saveAction = onSaveClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 4.dp)
        ) {
            TransactionTypeSelectionBox(
                selectedType = transactionType,
                onSelectionChanged = onTransactionTypeChange
            )
            AccountAndCategoryBoxRow(
                type = transactionType,
                fromAccount = fromAccount,
                toAccount = toAccount,
                category = category,
                setFromAccount = onFromAccountChange,
                setToAccount = onToAccountChange,
                setCategory = onCategoryChange,
            )
            Spacer(modifier = Modifier.height(4.dp))
            NoteInputBox(
                value = note,
                onValueChange = { note = it }
            )
            Spacer(modifier = Modifier.height(4.dp))
            CalculationBox(
                expression = expression,
                result = result,
                onPadClick = onPadClick,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DateTimeBox(
                onDateChange = onDateChange,
                onTimeChange = onTimeChange
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecordPreview() {
    AddRecordScreenContent(
        transactionType = TransactionType.INCOME,
        fromAccount = Account(name = "Cash", initialBalance = 0.0),
        toAccount = null,
        category = Category(name = "Salary", type = CategoryType.EXPENSE),
        expression = "0",
        result = "0",
        onTransactionTypeChange = {},
        onFromAccountChange = {},
        onToAccountChange = {},
        onCategoryChange = {},
        onPadClick = {},
        onDateChange = {},
        onTimeChange = {},
        onCancelClick = {},
        onSaveClick = {},
    )
}
