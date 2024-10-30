package com.hasib.moneytrack.service

import android.content.Context
import com.hasib.moneytrack.models.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(context: Context)
    suspend fun deleteAccount()
    suspend fun signOut()
}
