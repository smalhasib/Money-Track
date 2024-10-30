package com.hasib.moneytrack.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.hasib.moneytrack.models.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RecordRepository @Inject constructor(
    firestore: FirebaseFirestore
) {
    private val recordRef = firestore.collection("records")

    suspend fun <T : Transaction> addRecord(transaction: T) = withContext(Dispatchers.IO) {
        recordRef.add(transaction)
    }
}