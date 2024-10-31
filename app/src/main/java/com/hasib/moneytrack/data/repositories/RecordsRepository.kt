package com.hasib.moneytrack.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.hasib.moneytrack.models.Expense
import com.hasib.moneytrack.models.Income
import com.hasib.moneytrack.models.Transaction
import com.hasib.moneytrack.models.TransactionType
import com.hasib.moneytrack.models.Transfer
import com.hasib.moneytrack.service.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecordsRepository @Inject constructor(
    private val accountService: AccountService,
    firestore: FirebaseFirestore,
) {
    private val recordRef = firestore.collection(RECORDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    val records: Flow<List<Transaction>> = accountService.currentUser.flatMapLatest { user ->
        recordRef.whereEqualTo(USER_ID_FIELD, user.id)
            .orderBy(DATE_TIME_FIELD, Query.Direction.DESCENDING)
            .snapshots().map { snapshot ->
                snapshot.documents.mapNotNull { document ->
                    val type = document.getString("type")?.let {
                        TransactionType.valueOf(it)
                    } ?: return@mapNotNull null

                    when (type) {
                        TransactionType.EXPENSE -> document.toObject(Expense::class.java)
                        TransactionType.INCOME -> document.toObject(Income::class.java)
                        TransactionType.TRANSFER -> document.toObject(Transfer::class.java)
                    }
                }

            }
    }

    suspend fun <T : Transaction> addRecord(transaction: T) = withContext(Dispatchers.IO) {
        recordRef.add(transaction.copy(accountService.currentUserId))
    }

    companion object {
        private const val RECORDS_COLLECTION = "records"
        private const val USER_ID_FIELD = "userId"
        private const val DATE_TIME_FIELD = "dateTime"
    }
}