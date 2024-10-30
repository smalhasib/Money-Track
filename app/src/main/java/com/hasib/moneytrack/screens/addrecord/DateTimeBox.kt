package com.hasib.moneytrack.screens.addrecord

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.helpers.extensions.toLocalDate
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.hasib.moneytrack.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeBox(
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit
) {
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = ZonedDateTime.now().toInstant().toEpochMilli(),
        yearRange = 2000..selectedDate.year,
    )
    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime.hour,
        initialMinute = selectedTime.minute,
        is24Hour = false
    )


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        TextButton(
            onClick = { showDateDialog = true },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = selectedDate.format(DateTimeFormatter.ofPattern("dd MMM, yyyy")),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (showDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showDateDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDate = datePickerState.selectedDateMillis?.toLocalDate()
                            onDateChange(selectedDate)
                            showDateDialog = false
                        }
                    ) {
                        Text(stringResource(AppText.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDateDialog = false }) {
                        Text(stringResource(AppText.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .padding(horizontal = 4.dp),
            thickness = 2.dp
        )
        TextButton(
            onClick = { showTimeDialog = true },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (showTimeDialog) {
            AlertDialog(
                onDismissRequest = { showTimeDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedTime = LocalTime.of(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                            onTimeChange(selectedTime)
                            showTimeDialog = false
                        }
                    ) {
                        Text(stringResource(AppText.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTimeDialog = false }) {
                        Text(stringResource(AppText.cancel))
                    }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateTimeBoxPreview() {
    DateTimeBox({}, {})
}
