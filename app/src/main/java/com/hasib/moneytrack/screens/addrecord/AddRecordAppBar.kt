package com.hasib.moneytrack.screens.addrecord

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordAppBar(
    cancelAction: () -> Unit,
    saveAction: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            TextButton(
                onClick = cancelAction
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "CANCEL",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        actions = {
            TextButton(
                onClick = saveAction
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SAVE",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddRecordAppBarPreview() {
    AddRecordAppBar(
        cancelAction = { },
        saveAction = { }
    )
}
