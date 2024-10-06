package com.hasib.moneytrack.screens.add_record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hasib.moneytrack.screens.add_record.helpers.TransactionTypeSelectionBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    navController: NavController,
    viewModel: AddRecordViewModel = hiltViewModel<AddRecordViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    var note by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            AddRecordAppBar(
                navController = navController,
                saveAction = { }
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
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(4.dp))
            NoteInputBox(
                value = note,
                onValueChange = { note = it }
            )
            Spacer(modifier = Modifier.height(4.dp))
            CalculationBox(viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecordPreview() {
    AddRecordScreen(navController = rememberNavController())
}
