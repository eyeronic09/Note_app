package com.example.noteapp.TodoFeature.HomeScreen.UiLayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.noteapp.R
import com.example.noteapp.TodoFeature.AddScreen._TodoAddScreen
import org.koin.androidx.compose.koinViewModel

object TodoTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "Todos",
            icon = painterResource(R.drawable.outline_notes_24)
        )

    @Composable
    override fun Content() {
        Navigator(TodoHomeScreen())
    }
}

class TodoHomeScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        TodoHomeScreenMain()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoHomeScreenMain(viewModel: TodoHomeScreenVM = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val event = viewModel::onTodoEvent


    TodoHomeScreen(
        state = state,
        onAction =  event
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoHomeScreen(
    state : HomeScreenUiState,
    onAction :(TodoHomeScreenEvent) -> Unit
){
    val navigator = LocalNavigator.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = {
                    navigator?.parent?.parent?.push(_TodoAddScreen())
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add todo",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        TodoHomeContentMain(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            navigator = navigator,
            onEvent = onAction
        )
    }



}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoHomeContentMain(
    state: HomeScreenUiState,
    navigator: Navigator?,
    modifier: Modifier,
    onEvent: (TodoHomeScreenEvent) -> Unit
) {

    navigator?.let {
        TodoHomeScreen(
            state = state,
            onAction = onEvent,
            navigator = it,
            modifier = modifier
        )
    }
}
