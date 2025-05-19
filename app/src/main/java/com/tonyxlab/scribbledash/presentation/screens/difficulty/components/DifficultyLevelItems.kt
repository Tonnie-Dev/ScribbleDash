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
import com.tonyxlab.scribbledash.domain.model.DifficultyLevel
import com.tonyxlab.scribbledash.presentation.core.components.AppLabelText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.difficulty.handling.DifficultyUiEvent
import timber.log.Timber

@Composable
fun DifficultyItems(
    onSelectDifficultyLevel: (DifficultyUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val difficultyLevelHolders = listOf(
            DifficultyLevelHolder(
                    title = stringResource(R.string.text_beginner),
                    icon = R.drawable.level_beginner,
                    difficultyLevel = DifficultyLevel.BEGINNER
            ),
            DifficultyLevelHolder(
                    title = stringResource(R.string.text_challenging),
                    icon = R.drawable.level_challenging,
                    difficultyLevel = DifficultyLevel.CHALLENGING
            ),
            DifficultyLevelHolder(
                    title = stringResource(R.string.text_master),
                    icon = R.drawable.level_master,
                    difficultyLevel = DifficultyLevel.MASTER
            )
    )

    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
    ) {

        difficultyLevelHolders.forEachIndexed { i, item ->

            val topPadding = when (item.difficultyLevel) {
                DifficultyLevel.CHALLENGING -> MaterialTheme.spacing.spaceDefault
                else -> MaterialTheme.spacing.spaceLarge
            }

            val endPadding = when (item.difficultyLevel) {
                DifficultyLevel.MASTER -> MaterialTheme.spacing.spaceDefault
                else -> MaterialTheme.spacing.spaceLarge
            }

            DifficultyLevelItem(
                    modifier = Modifier.padding(top = topPadding, end = endPadding),
                    difficultyLevel = item,
                    onSelectDifficultyLevel = {

                        when (item.difficultyLevel) {

                            DifficultyLevel.BEGINNER -> {
                                onSelectDifficultyLevel(
                                        DifficultyUiEvent.OnSelectMasterLevel(
                                                DifficultyLevel.BEGINNER
                                        )
                                )

                            }

                            DifficultyLevel.CHALLENGING -> {

                                onSelectDifficultyLevel(
                                        DifficultyUiEvent.OnSelectMasterLevel(
                                                DifficultyLevel.CHALLENGING
                                        )
                                )
                            }

                            DifficultyLevel.MASTER -> {

                                onSelectDifficultyLevel(
                                        DifficultyUiEvent.OnSelectMasterLevel(
                                                DifficultyLevel.MASTER
                                        )
                                )
                            }
                        }


                    }
            )
        }
    }
}


@Composable
fun DifficultyLevelItem(
    difficultyLevel: DifficultyLevelHolder,
    onSelectDifficultyLevel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier.clickable { onSelectDifficultyLevel() },
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

data class DifficultyLevelHolder(
    @DrawableRes val icon: Int,
    val title: String,
    val difficultyLevel: DifficultyLevel
)


