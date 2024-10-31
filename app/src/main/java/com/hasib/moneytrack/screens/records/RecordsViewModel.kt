package com.hasib.moneytrack.screens.records

import androidx.lifecycle.viewModelScope
import com.hasib.moneytrack.R
import com.hasib.moneytrack.base.BaseViewModel
import com.hasib.moneytrack.common.snackbar.SnackBarManager
import com.hasib.moneytrack.common.util.Async
import com.hasib.moneytrack.common.util.WhileUiSubscribed
import com.hasib.moneytrack.data.repositories.RecordsRepository
import com.hasib.moneytrack.models.Transaction
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    recordsRepository: RecordsRepository,
    logService: LogService,
    navigationService: NavigationService
) : BaseViewModel(logService, navigationService) {
    private val _recordsAsync = recordsRepository.records.map {
        Async.Success(it)
    }.catch<Async<List<Transaction>>> { throwable ->
        Timber.e(throwable)
        SnackBarManager.showMessage(R.string.loading_records_error)
        emit(Async.Error(R.string.loading_records_error))
    }

    val uiState: StateFlow<RecordsUiState> = _recordsAsync.map {
        when (it) {
            Async.Loading -> RecordsUiState(isLoading = true)
            is Async.Error -> RecordsUiState(
                items = emptyList(),
                isLoading = false
            )

            is Async.Success -> RecordsUiState(
                items = it.data,
                isLoading = false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = RecordsUiState(isLoading = true)
    )
}