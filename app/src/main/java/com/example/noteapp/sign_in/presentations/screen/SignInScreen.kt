package com.example.noteapp.sign_in.presentations.screen

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.noteapp.MainScreen
import com.example.noteapp.R
import com.example.noteapp.sign_in.presentations.state.AuthUiState
import com.example.noteapp.sign_in.presentations.state.SignInEvent
import com.example.noteapp.sign_in.presentations.state.SignInState
import com.example.noteapp.sign_in.presentations.state.SignInViewModel
import com.example.noteapp.ui.theme.NoteAppTheme
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

    LaunchedEffect(uiState.authState) {
        when (val state = uiState.authState) {
            is AuthUiState.Success -> {
                Toast.makeText(
                    context,
                    "Sign in successful! Welcome ${state.user?.displayName ?: ""}",
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
        onEvent = viewModel::onUiEvent
    )
}

@Composable
fun SingInScreen(
    uiState: SignInState,
    onEvent: (SignInEvent) -> Unit
) {
    val context = LocalContext.current
    val isLoading = uiState.authState is AuthUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SigninText(
            email = uiState.email,
            password = uiState.password,
            onEmailChange = { onEvent(SignInEvent.EmailChanged(it)) },
            onPasswordChange = { onEvent(SignInEvent.PasswordChanged(it)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        GoogleSignInButtonUi(
            text = "Sign In with Google",
            loadingText = "Signing In...",
            isLoading = isLoading,
            onClicked = { onEvent(SignInEvent.ContinueWithGoogle(context)) }
        )
    }
}

@Composable
fun SigninText(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.gemini_generated_image_15ehr915ehr915eh_removebg_preview),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sign in to sync with cloud",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Keep your notes, journals, and voice memos backed up securely across devices.",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
        }
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

@Preview(showBackground = true)
@Composable
private fun SingInScreenPreview() {
    NoteAppTheme {
        SingInScreen(
            uiState = SignInState(),
            onEvent = {}
        )
    }
}
