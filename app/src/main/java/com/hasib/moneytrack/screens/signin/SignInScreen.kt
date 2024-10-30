package com.hasib.moneytrack.screens.signin

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hasib.moneytrack.R

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity

    SignInScreenContent(
        onGoogleSignInClick = {
            viewModel.onGoogleSignInClick(context)
        }
    )
}

@Composable
private fun SignInScreenContent(onGoogleSignInClick: () -> Unit) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ElevatedButton(
            modifier = Modifier.offset(y = 150.dp),
            onClick = onGoogleSignInClick
        ) {
            Image(
                painterResource(id = R.drawable.google),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Continue with Google",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreenContent(
        onGoogleSignInClick = {}
    )
}
