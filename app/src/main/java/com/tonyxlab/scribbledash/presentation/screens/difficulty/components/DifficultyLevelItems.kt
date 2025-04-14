package com.tonyxlab.scribbledash.presentation.screens.difficulty.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppLabelText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing

@Composable
fun DifficultyItems(
    onSelectDifficultyLevel: (DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    val difficultyLevels = listOf(
            DifficultyLevel(
                    title = stringResource(R.string.text_beginner),
                    icon = R.drawable.level_beginner,
                    level = Level.BEGINNER
            ),
            DifficultyLevel(
                    title = stringResource(R.string.text_challenging),
                    icon = R.drawable.level_challenging,
                    level = Level.CHALLENGING
            ),
            DifficultyLevel(
                    title = stringResource(R.string.text_master),
                    icon = R.drawable.level_master,
                    level = Level.MASTER
            )
    )

    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
    ) {

        difficultyLevels.forEachIndexed { i, item ->
            val topPadding = when (item.level) {
                Level.CHALLENGING -> MaterialTheme.spacing.spaceDefault
                else -> MaterialTheme.spacing.spaceLarge
            }
            val endPadding = when (item.level) {
                Level.MASTER -> MaterialTheme.spacing.spaceDefault
                else -> MaterialTheme.spacing.spaceLarge
            }
            DifficultyLevelItem(
                    modifier = Modifier.padding(top = topPadding, end = endPadding),
                    difficultyLevel = item,
                    onSelectDifficultyLevel = { onSelectDifficultyLevel(item) }
            )
        }

    }

}


@Composable
fun DifficultyLevelItem(
    difficultyLevel: DifficultyLevel,
    onSelectDifficultyLevel: (DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier.clickable { onSelectDifficultyLevel(difficultyLevel) },
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
                painter = painterResource(difficultyLevel.icon),
                contentDescription = difficultyLevel.title,
                contentScale = ContentScale.Fit
        )

        AppLabelText(text = difficultyLevel.title)
    }
}

@PreviewLightDark
@Composable
private fun DifficultyItemPreview() {
    Column(
            modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.Center
    ) {
        DifficultyItems(onSelectDifficultyLevel = { })
    }
}

data class DifficultyLevel(@DrawableRes val icon: Int, val title: String, val level: Level)

enum class Level { BEGINNER, CHALLENGING, MASTER }
