package com.hasib.moneytrack.screens.add_record.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.models.TransactionType

@Composable
fun TransactionTypeSelectionBox(
    selectedType: TransactionType,
    onSelectionChanged: (TransactionType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 8.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransactionType.entries.forEach { type ->
            if (type == TransactionType.EXPENSE) {
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    thickness = 2.dp
                )
            }
            CheckBoxWithLabel(
                type = type,
                selected = selectedType == type,
                onSelectionChanged = onSelectionChanged
            )
            if (type == TransactionType.EXPENSE) {
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    thickness = 2.dp
                )
            }
        }
    }
}

@Composable
private fun CheckBoxWithLabel(
    type: TransactionType,
    selected: Boolean,
    onSelectionChanged: (TransactionType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable {
                onSelectionChanged(type)
            }
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Add Record",
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = type.name.toUpperCase(Locale.current),
            style = MaterialTheme.typography.titleMedium,
            color = if (selected) MaterialTheme.colorScheme.secondary else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionTypeSelectionBoxPreview() {
    TransactionTypeSelectionBox(
        selectedType = TransactionType.EXPENSE,
        onSelectionChanged = {}
    )
}
