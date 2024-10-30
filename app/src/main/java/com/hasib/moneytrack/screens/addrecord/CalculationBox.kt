package com.hasib.moneytrack.screens.addrecord

import androidx.annotation.StringRes
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.R.string as AppText

private val numberPad = listOf(
    listOf(AppText.pad_ac, AppText.pad_parentheses, AppText.pad_percent, AppText.pad_backspace),
    listOf(AppText.pad_add, AppText.pad_7, AppText.pad_8, AppText.pad_9),
    listOf(AppText.pad_subtract, AppText.pad_4, AppText.pad_5, AppText.pad_6),
    listOf(AppText.pad_multiply, AppText.pad_1, AppText.pad_2, AppText.pad_3),
    listOf(AppText.pad_divide, AppText.pad_dot, AppText.pad_0, AppText.pad_equals)
)

private val operators = listOf(
    AppText.pad_add,
    AppText.pad_subtract,
    AppText.pad_multiply,
    AppText.pad_divide,
    AppText.pad_equals,
    AppText.pad_percent,
    AppText.pad_ac,
    AppText.pad_parentheses,
    AppText.pad_backspace
)

@Composable
fun CalculationBox(
    expression: String,
    result: String,
    onPadClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
                    text = expression,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1
                )
                Text(
                    text = result,
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
                            .fillMaxHeight(),
                        onClick = onPadClick
                    )
                }
            }
        }
    }
}

@Composable
private fun PadBox(
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    onClick: (String) -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    val labelText = stringResource(label)
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
            onClick(labelText)
        },
        contentAlignment = Alignment.Center
    ) {
        if (label == AppText.pad_backspace) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Backspace,
                contentDescription = stringResource(AppText.pad_backspace),
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Text(
                text = labelText,
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
    CalculationBox(
        expression = "123",
        result = "456",
        onPadClick = {}
    )
}
