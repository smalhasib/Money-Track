package com.hasib.moneytrack.screens.add_record

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.data.AppData
import com.hasib.moneytrack.models.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountAndCategoryBoxRow(
    type: TransactionType,
    viewModel: AddRecordViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isAccount = false

    // TODO: add category selection, update ui to show selected account & category

    Column {
        Row {
            AccountAndCategoryBox(
                modifier = Modifier.weight(1f),
                title = if (type == TransactionType.TRANSFER) "From Account" else "Account",
                imageVector = Icons.Default.AccountBalanceWallet,
                onClick = {
                    isAccount = true
                    showBottomSheet = true
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            AccountAndCategoryBox(
                modifier = Modifier.weight(1f),
                title = if (type == TransactionType.TRANSFER) "From Account" else "Category",
                imageVector = Icons.Default.Sell,
                onClick = {
                    isAccount = false
                    showBottomSheet = true
                }
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showBottomSheet = false
                },
            ) {
                LazyColumn {
                    items(
                        items = AppData.accounts,
                        key = { account -> account.name }
                    ) { account ->
                        ListItem(
                            modifier = Modifier.selectable(
                                selected = account == uiState.fromAccount,
                                onClick = {
                                    viewModel.setFromAccount(account)
                                    showBottomSheet = false
                                }
                            ),
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            ),
                            headlineContent = {
                                Text(
                                    text = account.name,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            leadingContent = {
                                val icon = AppData.accountIcons[account.iconIndex]
                                Image(
                                    painter = painterResource(icon),
                                    contentDescription = account.name,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        )
                        if (account != AppData.accounts.last()) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun AccountAndCategoryBox(
    modifier: Modifier = Modifier,
    title: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = title,
                modifier = Modifier
                    .size(30.dp)
                    .rotate(
                        if (title == "Category") 90f else 0f
                    ),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccountAndCategoryBoxPreview() {
    AccountAndCategoryBox(
        title = "Account",
        imageVector = Icons.Default.AccountBalanceWallet,
        onClick = {}
    )
}
