package com.hasib.moneytrack.screens.signin

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hasib.moneytrack.BuildConfig
import com.hasib.moneytrack.helpers.extensions.showToast
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.navigation.Navigator
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.security.MessageDigest
import java.util.UUID

class SignInViewModel(
    private val credentialManager: CredentialManager,
    private val navigator: Navigator
) : ViewModel() {

    fun handleGoogleSignIn(context: Context) {
        viewModelScope.launch {
            googleSignIn(context).collect { result ->
                result.fold(
                    onSuccess = {
                        navigator.navigateTo(
                            destination = Destination.HomeGraph,
                            navOptions = {
                                popUpTo(Destination.AuthGraph) {
                                    inclusive = true
                                }
                            }
                        )
                        context.showToast("Welcome to MoneyTrack!")
                    },
                    onFailure = { e ->
                        Timber.e(e)
                        context.showToast("Failed to sign in. Please try again.")
                    }
                )
            }
        }
    }

    private suspend fun googleSignIn(context: Context): Flow<Result<AuthResult>> {
        val firebaseAuth = FirebaseAuth.getInstance()
        return callbackFlow {
            try {
                // Generate a nonce (a random number used once)
                val ranNonce: String = UUID.randomUUID().toString()
                val bytes: ByteArray = ranNonce.toByteArray()
                val md: MessageDigest = MessageDigest.getInstance("SHA-256")
                val digest: ByteArray = md.digest(bytes)
                val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }

                // Set up Google ID option
                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.FIREBASE_WEB_CLIENT_ID)
                    .setNonce(hashedNonce)
                    .build()

                // Request credentials
                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                // Get the credential result
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                // Check if the received credential is a valid Google ID Token
                if (
                    credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val idTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val authCredential = GoogleAuthProvider.getCredential(
                        idTokenCredential.idToken,
                        null
                    )
                    val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                    trySend(Result.success(authResult))
                } else {
                    throw RuntimeException("Received an invalid credential type")
                }
            } catch (e: GetCredentialCancellationException) {
                trySend(Result.failure(Exception("Sign-in was canceled. Please try again.")))

            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
            awaitClose { }
        }
    }
}