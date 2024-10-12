package com.hasib.moneytrack.screens.add_record

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.data.AppUserManager

private val numberPad = listOf(
    listOf("AC", "( )", "%", "bs"),
    listOf("+", "7", "8", "9"),
    listOf("−", "4", "5", "6"),
    listOf("×", "1", "2", "3"),
    listOf("÷", ".", "0", "=")
)

private val operators = listOf("+", "−", "×", "÷", "=", "%", "AC", "( )", "bs")

@Composable
fun CalculationBox(
    viewModel: AddRecordViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = uiState.expression,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1
                )
                Text(
                    text = uiState.result,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        numberPad.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { item ->
                    PadBox(
                        label = item,
                        filled = operators.contains(item),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        viewModel.handleClick(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun PadBox(
    label: String,
    filled: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = if (filled) {
            modifier
                .padding(1.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
        } else {
            modifier
                .padding(1.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        }.clickable {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick(label)
        },
        contentAlignment = Alignment.Center
    ) {
        if (label == "bs") {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Backspace,
                contentDescription = "Backspace",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = if (filled) MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculationBoxPreview() {
    CalculationBox(viewModel = AddRecordViewModel(AppUserManager()))
}
