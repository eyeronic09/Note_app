package com.example.noteapp.sign_in.presentations.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.noteapp.sign_in.domain.model.UserData
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import com.example.noteapp.sign_in.presentations.UiState.CurrentUserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

sealed interface CurrentUiEvent {
    data object Logout : CurrentUiEvent
}

class CurrentScreenVM(
    private val repository: AuthReposistory
) : ViewModel() {
    private val _uiState = MutableStateFlow<CurrentUserUiState>(value = CurrentUserUiState.Loading)
    val uiState: StateFlow<CurrentUserUiState> = _uiState.asStateFlow()

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        val userData = repository.getCurrentUserData()
        if (userData != null) {
            _uiState.value = CurrentUserUiState.Success(userData)
        } else {
            _uiState.value = CurrentUserUiState.LoggedOut
        }
    }

    fun onEvent(event: CurrentUiEvent) {
        when (event) {
            is CurrentUiEvent.Logout -> {
                viewModelScope.launch {
                    repository.signOut()
                    _uiState.value = CurrentUserUiState.LoggedOut
                }
            }
        }
    }
}

class CurrentUserScreenRoute : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        CurrentScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentScreen(viewModel: CurrentScreenVM = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigator = LocalNavigator.current

    LaunchedEffect(uiState) {
        if (uiState is CurrentUserUiState.LoggedOut) {
            navigator?.replace(SignInScreenRouter())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Account", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navigator?.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is CurrentUserUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is CurrentUserUiState.LoggedOut -> {
                    CircularProgressIndicator()
                }
                is CurrentUserUiState.Success -> {
                    ProfileContent(
                        userData = state.userData,
                        onLogout = { viewModel.onEvent(CurrentUiEvent.Logout) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    userData: UserData,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Avatar
        val initial = userData.username?.firstOrNull()?.uppercaseChar()?.toString() ?: "U"
        Surface(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = initial,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Name
        Text(
            text = userData.username ?: "Note App User",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Signed in",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Details Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                ProfileDetailItem(
                    icon = Icons.Default.Person,
                    label = "Account Username",
                    value = userData.username ?: "N/A"
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileDetailItem(
                    icon = Icons.Default.Badge,
                    label = "User ID",
                    value = userData.userId
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileDetailItem(
                    icon = Icons.Default.CloudDone,
                    label = "Cloud Sync Status",
                    value = "Active & Synced"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sign Out Button
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = SolidColor(MaterialTheme.colorScheme.error)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign Out",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ProfileDetailItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
