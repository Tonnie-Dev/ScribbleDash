@file:OptIn(ExperimentalMaterial3Api::class)

package com.tonyxlab.scribbledash.presentation.core.components

import android.R.attr.contentDescription
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme


@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    isShowCancelButton: Boolean = false,
    onCancelClick: (() -> Unit)? = null
) {

    TopAppBar(
            modifier = modifier,
            title = {
                if (title.isNotBlank()) {
                    Text(
                            modifier =
                                Modifier
                                        .fillMaxWidth()
                                        .padding(end = MaterialTheme.spacing.spaceLarge),
                            text = title,
                            textAlign = TextAlign.Start,
                            style = style.copy(color = MaterialTheme.colorScheme.onBackground),
                    )
                }
            },
            actions = {
                if (isShowCancelButton) {
                    IconButton(onClick = { onCancelClick?.invoke() }) {
                        Icon(
                                modifier = Modifier
                                        .size(56.dp)
                                        .align(Alignment.CenterVertically),
                                painter = painterResource(R.drawable.ic_close_circle),
                                contentDescription = stringResource(R.string.text_cancel),
                                tint = MaterialTheme.colorScheme.onSurface

                        )
                    }
                }
            },
    )
}

@PreviewLightDark
@Composable
private fun AppTopBarPreview() {

    ScribbleDashTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
        }

        AppTopBar(title = "ScribbleDash", isShowCancelButton = true)
        AppTopBar(title = "Start Drawing!", isShowCancelButton = false)


    }

}
