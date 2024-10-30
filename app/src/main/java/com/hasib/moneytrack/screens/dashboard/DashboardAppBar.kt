package com.hasib.moneytrack.screens.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import com.hasib.moneytrack.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAppBar(drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(text = stringResource(AppText.app_name))
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(AppText.menu)
                )
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(AppText.search)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}