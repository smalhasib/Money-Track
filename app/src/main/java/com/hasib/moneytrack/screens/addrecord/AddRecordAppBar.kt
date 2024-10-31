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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordAppBar(
    disableSaveButton: Boolean,
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
                        contentDescription = stringResource(AppText.cancel)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(AppText.cancel),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        actions = {
            TextButton(
                onClick = saveAction,
                enabled = !disableSaveButton
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(AppText.save)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(AppText.save),
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
        disableSaveButton = false,
        cancelAction = { },
        saveAction = { }
    )
}
