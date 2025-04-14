package com.tonyxlab.scribbledash.presentation.screens.difficulty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppHeadlineText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.R

@Composable
fun DifficultyLevelScreenContent(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { onClose }) {
            Icon(
                    painter = painterResource(R.drawable.ic_close_circle),
                    contentDescription = stringResource(id = R.string.text_close),
                    tint = MaterialTheme.colorScheme.onSurface
            )

        }

        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.spaceSingleDp * 55),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AppHeadlineText(
                    text = stringResource(id = R.string.button_text_start_drawing),
                    textStyle = MaterialTheme.typography.displayMedium
            )
            AppBodyText(text = stringResource(id = R.string.text_select_game_mode))
        }
    }


}