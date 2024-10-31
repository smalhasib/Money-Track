package com.hasib.moneytrack.screens.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.common.extensions.toLocalDateTime
import com.hasib.moneytrack.data.AppData
import java.time.format.DateTimeFormatter

@Composable
fun RecordsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SummeryBox()
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(AppData.transactions.groupBy { it.dateTime.toLocalDateTime().toLocalDate() }
                .toList()) { (date, transactions) ->
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("MMM dd, EEEE")),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                transactions.forEach { transaction ->
                    TransactionItem(transaction = transaction)
                    if (transaction != transactions.last()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 80.dp, end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordsPagePreview() {
    RecordsScreen()
}
