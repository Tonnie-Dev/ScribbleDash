package com.tonyxlab.scribbledash.presentation.screens.difficulty

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.navigation.NavOperations
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.difficulty.components.DifficultyItems
import com.tonyxlab.scribbledash.presentation.screens.difficulty.components.DifficultyLevel
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme


@Composable
fun DifficultyLevelScreen(
    navOperations: NavOperations,
    onSelectDifficultyLevel: (DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->

        DifficultyLevelScreenContent(
                modifier = modifier.padding(innerPadding),
                onClose = { navOperations.popBackStack() },
                onBackPress = {navOperations.navigateToHomeDestination()},
                onSelectDifficultyLevel = onSelectDifficultyLevel
        )

    }
}

@Composable
fun DifficultyLevelScreenContent(
    onClose: () -> Unit,
    onBackPress:() -> Unit,
    onSelectDifficultyLevel: (DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier
) {

    BackHandler {  onBackPress()}
    Column(
            modifier = modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppCloseIcon(onClose = onClose)

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


        DifficultyItems(onSelectDifficultyLevel = onSelectDifficultyLevel)
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

            DifficultyLevelScreenContent(onClose = {}, onSelectDifficultyLevel = {}, onBackPress = {})
        }
    }
}
