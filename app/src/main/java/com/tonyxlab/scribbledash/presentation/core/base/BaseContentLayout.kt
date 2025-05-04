package com.tonyxlab.scribbledash.presentation.core.base

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.tonyxlab.scribbledash.presentation.core.base.handling.ActionEvent
import com.tonyxlab.scribbledash.presentation.core.base.handling.UiEvent
import com.tonyxlab.scribbledash.presentation.core.base.handling.UiState
import kotlinx.coroutines.CoroutineScope

@Composable
fun <S : UiState, E : UiEvent, A : ActionEvent> BaseContentLayout(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel<S, E, A>,
    onBackPressed: (() -> Unit)? = null,
    topBar: @Composable (uiState: UiState) -> Unit = {},
    bottomBar: @Composable (uiState: UiState) -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    actionEventHandler: (suspend CoroutineScope.(context: Context, actionEvent: A) -> Unit)? = null,
    content: @Composable (BoxScope.(uiState: S) -> Unit)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    LaunchedEffect(key1 = lifecycle) {

        if (actionEventHandler != null) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.actionEvent.collect { actionEvent ->
                    actionEventHandler(context, actionEvent)
                }
            }
        }
    }

    BackHandler(
            enabled = onBackPressed != null,
            onBack = { onBackPressed?.invoke() }
    )

    Surface(
            modifier = Modifier.fillMaxSize(),
            color = containerColor
    ) {
        Scaffold(
                modifier = Modifier
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .fillMaxWidth(),
                topBar = { topBar(uiState) },
                bottomBar = { bottomBar(uiState) },
                snackbarHost = snackbarHost,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                containerColor = containerColor,
                contentColor = contentColor,
                contentWindowInsets = contentWindowInsets
        ) { paddingValues ->

            Box(modifier = modifier.padding(paddingValues)) {
                content(uiState)
            }
        }

    }

}