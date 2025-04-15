package com.tonyxlab.scribbledash.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

@Composable
fun AppCloseIcon(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
            modifier = modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.spaceTwelve * 6)
                    .padding(vertical = MaterialTheme.spacing.spaceMedium),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
                modifier = Modifier.padding(end = MaterialTheme.spacing.spaceMedium),
                onClick = { onClose() }) {
            Icon(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceSmall * 7),
                    painter = painterResource(R.drawable.ic_close_circle),
                    contentDescription = stringResource(id = R.string.text_close),
                    tint = MaterialTheme.colorScheme.onSurface
            )

        }
    }

}
@PreviewLightDark
@Composable
fun AppCloseIconPreview(modifier: Modifier = Modifier) {

    ScribbleDashTheme {

        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            AppCloseIcon(onClose = {})

        }

    }

}
