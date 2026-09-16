package com.example.noteapp.sign_in.presentations.screen

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.noteapp.MainScreen
import com.example.noteapp.R
import com.example.noteapp.sign_in.presentations.state.AuthUiState
import com.example.noteapp.sign_in.presentations.state.SignInViewModel
import org.koin.androidx.compose.koinViewModel

class SignInScreenRouter : Screen {
    @Composable
    override fun Content() {
        _SingInScreen()
    }
}

@Composable
fun _SingInScreen(viewModel: SignInViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navigator = LocalNavigator.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> {
                Toast.makeText(
                    context,
                    "Sign in successful! Welcome ${state.user.displayName ?: ""}",
                    Toast.LENGTH_SHORT
                ).show()
                navigator?.replace(MainScreen())
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    SingInScreen(
        uiState = uiState,
        onSignInClick = {
            viewModel.signInWithGoogle(context)
        }
    )
}

@Composable
fun SingInScreen(uiState: AuthUiState, onSignInClick: () -> Unit) {
    val isLoading = uiState is AuthUiState.Loading

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GoogleSignInButtonUi(
            text = "Sign In with Google",
            loadingText = "Signing In...",
            isLoading = isLoading,
            onClicked = onSignInClick
        )
    }
}

@Composable
fun GoogleSignInButtonUi(
    text: String,
    loadingText: String,
    isLoading: Boolean = false,
    onClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(enabled = !isLoading) { onClicked() },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (isLoading) loadingText else text)

            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
