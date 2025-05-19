package com.tonyxlab.scribbledash.presentation.screens.difficulty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.domain.model.DifficultyLevel
import com.tonyxlab.scribbledash.navigation.NavOperations
import com.tonyxlab.scribbledash.presentation.core.base.BaseContentLayout
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.difficulty.components.DifficultyItems
import com.tonyxlab.scribbledash.presentation.screens.difficulty.handling.DifficultyActionEvent
import com.tonyxlab.scribbledash.presentation.screens.difficulty.handling.DifficultyUiEvent
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun DifficultyLevelScreen(
    navOperations: NavOperations,
    modifier: Modifier = Modifier,
    viewModel: DifficultyViewModel = koinViewModel()
) {
    BaseContentLayout(
            viewModel = viewModel,
            actionEventHandler = { _, action ->

                when (action) {
                    DifficultyActionEvent.Exit -> {
                        navOperations.popBackStack()
                    }

                    is DifficultyActionEvent.NavigateToDrawScreen -> {
                        navOperations.navigateToDrawScreen(
                                gameMode = action.mode,
                                gameLevel = action.level
                        )
                    }
                }
            },
            onBackPressed = { navOperations.navigateToHomeDestination() }
    ) {

        DifficultyLevelScreenContent(
                modifier = modifier,
                onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun DifficultyLevelScreenContent(
    onEvent: (DifficultyUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    //BackHandler { onBackPress() }

    Column(
            modifier = modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppCloseIcon(onClose = { onEvent(DifficultyUiEvent.OnClose) })

        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                                top = MaterialTheme.spacing.spaceOneTwentyEight,
                                bottom = MaterialTheme.spacing.spaceSingleDp * 55
                        ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeaderText(
                    text = stringResource(id = R.string.button_text_start_drawing),
                    textStyle = MaterialTheme.typography.displayMedium
            )
            AppBodyText(text = stringResource(id = R.string.text_select_game_mode))
        }

        DifficultyItems(onSelectDifficultyLevel = onEvent)
    }

}

@PreviewLightDark
@Composable
private fun DifficultyLevelScreenContentPreview() {

    ScribbleDashTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
        ) {

            DifficultyLevelScreenContent(onEvent = {})
        }
    }
}
