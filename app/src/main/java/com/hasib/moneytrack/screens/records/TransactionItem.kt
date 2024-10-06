package com.hasib.moneytrack.screens.records

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hasib.moneytrack.R
import com.hasib.moneytrack.data.AppData
import com.hasib.moneytrack.models.Expense
import com.hasib.moneytrack.models.Income
import com.hasib.moneytrack.models.Transaction
import com.hasib.moneytrack.models.Transfer
import java.text.DecimalFormat

private data class TransactionItemValues(
    val title: String,
    val subtitle: String,
    val amountColor: Color,
)

@Composable
fun TransactionItem(transaction: Transaction) {
    var showDialog by remember { mutableStateOf(false) }

    val (title, subtitle, amountColor) = when (transaction) {
        is Income -> TransactionItemValues(
            title = transaction.category.name,
            subtitle = transaction.account.name,
            amountColor = Color.Green
        )

        is Expense -> TransactionItemValues(
            title = transaction.category.name,
            subtitle = transaction.account.name,
            amountColor = Color.Red
        )

        else -> TransactionItemValues(
            title = "Transfer",
            subtitle = "Transfer",
            amountColor = Color.Blue
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        ListItem(
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { showDialog = true },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            headlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingContent = {
                Image(
                    painter = painterResource(R.drawable.expenses),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
            },
            supportingContent = {
                if (transaction is Transfer) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SubtitleItem(transaction.fromAccount.name, R.drawable.bank)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Right Arrow",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SubtitleItem(transaction.toAccount.name, R.drawable.cash)
                    }
                } else {
                    SubtitleItem(subtitle, R.drawable.bank)
                }
            },
            trailingContent = {
                Text(
                    text = DecimalFormat("#.00").format(transaction.amount),
                    color = amountColor,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        )
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false }
            ) {
                TransactionItemDialogContent(
                    transaction = transaction,
                    closeAction = {
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SubtitleItem(subtitle: String, @DrawableRes icon: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = subtitle,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    TransactionItem(
        transaction = AppData.transactions[0]
    )
}