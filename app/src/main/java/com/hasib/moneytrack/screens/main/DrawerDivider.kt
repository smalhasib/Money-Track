package com.hasib.moneytrack.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DrawerDivider(sectionTitle: String) {
    HorizontalDivider(
        thickness = 2.dp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Text(
        text = sectionTitle,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
    )
}