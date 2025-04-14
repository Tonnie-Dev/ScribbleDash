package com.tonyxlab.scribbledash.presentation.screens.draw

import android.R.attr.text
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppButton
import com.tonyxlab.scribbledash.presentation.core.components.AppHeadlineText
import com.tonyxlab.scribbledash.presentation.core.components.AppIcon
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Success

@Composable
fun DrawScreen(modifier: Modifier = Modifier) {
    Column {
        AppHeadlineText(
                modifier = modifier.padding(bottom = MaterialTheme.spacing.spaceOneTwentyEight),
                text = stringResource(R.string.text_time_to_draw)
        )
        Row (horizontalArrangement = Arrangement.SpaceBetween){

            AppIcon(icon = R.drawable.ic_reply) { }
            AppIcon(icon = R.drawable.ic_forward) { }
            AppButton(contentColor = Success) { }
        }
    }

}


