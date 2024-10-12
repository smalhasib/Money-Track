package com.hasib.moneytrack.screens.add_record

import android.widget.Toast
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hasib.moneytrack.data.AppUserManager
import com.hasib.moneytrack.screens.add_record.helpers.TransactionTypeSelectionBox
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddRecordScreen(
    navController: NavController,
    viewModel: AddRecordViewModel = hiltViewModel<AddRecordViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    var note by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.error) {
        viewModel.error.collectLatest { value ->
            value?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            AddRecordAppBar(
                navController = navController,
                saveAction = {
                    if (viewModel.saveData()) {
                        navController.popBackStack()
                    }
                }
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
            CalculationBox(
                viewModel = viewModel,
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
        navController = rememberNavController(),
        viewModel = AddRecordViewModel(
            appUserManager = AppUserManager()
        )
    )
}
