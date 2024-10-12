package com.hasib.moneytrack.screens.add_record

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.data.AppData
import com.hasib.moneytrack.models.Account
import com.hasib.moneytrack.models.Category
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
    var isAccount by remember { mutableStateOf(false) }

    Column {
        Row {
            AccountAndCategoryBox(
                modifier = Modifier.weight(1f),
                title = uiState.fromAccount.name,
                imageVector = Icons.Default.AccountBalanceWallet,
                imageId = uiState.fromAccount.imageId,
                onClick = {
                    isAccount = true
                    showBottomSheet = true
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            val (title, imageId) = when {
                type == TransactionType.TRANSFER && uiState.toAccount != null -> (uiState.toAccount!!.name to uiState.toAccount!!.imageId)
                type != TransactionType.TRANSFER && uiState.category != null -> (uiState.category!!.name to uiState.category!!.imageId)
                else -> ("Category" to -1)
            }
            AccountAndCategoryBox(
                modifier = Modifier.weight(1f),
                title = title,
                imageVector = Icons.Default.Sell,
                imageId = imageId,
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
                val lastItems = listOf(
                    AppData.accounts.last(),
                    AppData.incomeCategories.last(),
                    AppData.expenseCategories.last()
                )
                LazyColumn {
                    items(
                        items = when {
                            isAccount -> AppData.accounts
                            uiState.transactionType == TransactionType.INCOME -> AppData.incomeCategories
                            else -> AppData.expenseCategories
                        },
                        key = { account -> account.name }
                    ) { item ->
                        ListItem(
                            modifier = Modifier.selectable(
                                selected = item == uiState.fromAccount,
                                onClick = {
                                    when {
                                        isAccount -> viewModel.setFromAccount(item as Account)
                                        uiState.transactionType == TransactionType.TRANSFER -> viewModel.setToAccount(
                                            item as Account
                                        )

                                        else -> viewModel.setCategory(item as Category)
                                    }
                                    showBottomSheet = false
                                }
                            ),
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            ),
                            headlineContent = {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            leadingContent = {
                                val icon = item.imageId
                                Image(
                                    painter = painterResource(icon),
                                    contentDescription = item.name,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        )
                        if (item !in lastItems) {
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
    @DrawableRes imageId: Int = -1,
    imageVector: ImageVector? = null,
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
            if (imageId != -1) {
                Image(
                    painter = painterResource(imageId),
                    contentDescription = title,
                    modifier = Modifier.size(28.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                Icon(
                    imageVector = imageVector!!,
                    contentDescription = title,
                    modifier = Modifier
                        .size(28.dp)
                        .rotate(
                            if (title == "Category") 90f else 0f
                        ),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
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
