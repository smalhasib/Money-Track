package com.hasib.moneytrack.screens.addrecord

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.data.AppUserManager
import com.hasib.moneytrack.helpers.extensions.showToast
import com.hasib.moneytrack.navigation.DefaultNavigator
import com.hasib.moneytrack.screens.addrecord.helpers.TransactionTypeSelectionBox
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecordScreen(
    viewModel: AddRecordViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var note by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.error) {
        viewModel.error.collectLatest { value ->
            value?.let {
                context.showToast(it)
            }
        }
    }

    Scaffold(
        topBar = {
            AddRecordAppBar(
                cancelAction = { viewModel.navigateUp() },
                saveAction = { viewModel.saveData() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 4.dp)
        ) {
            TransactionTypeSelectionBox(
                selectedType = uiState.transactionType,
                onSelectionChanged = { viewModel.setTransactionType(it) }
            )
            AccountAndCategoryBoxRow(
                type = uiState.transactionType,
                fromAccount = uiState.fromAccount,
                toAccount = uiState.toAccount,
                category = uiState.category,
                setFromAccount = { viewModel.setFromAccount(it) },
                setToAccount = { viewModel.setToAccount(it) },
                setCategory = { viewModel.setCategory(it) },
            )
            Spacer(modifier = Modifier.height(4.dp))
            NoteInputBox(
                value = note,
                onValueChange = { note = it }
            )
            Spacer(modifier = Modifier.height(4.dp))
            CalculationBox(
                expression = uiState.expression,
                result = uiState.result,
                onPadClick = { viewModel.handleClick(it) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DateTimeBox(
                onDateChange = { viewModel.setDate(it) },
                onTimeChange = { viewModel.setTime(it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecordPreview() {
    AddRecordScreen(
        viewModel = AddRecordViewModel(
            appUserManager = AppUserManager(),
            navigator = DefaultNavigator()
        )
    )
}
