package com.tonyxlab.scribbledash.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.Success
import com.tonyxlab.scribbledash.utils.spacing

@Composable
fun AppButton(
    contentColor: Color,
    modifier: Modifier = Modifier,
    buttonText: String = stringResource(R.string.button_text_start),
    radius: Dp = MaterialTheme.spacing.spaceMedium,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
            modifier = modifier
                    .clip(RoundedCornerShape(radius))
                    .shadow(
                            elevation = MaterialTheme.spacing.spaceSmall,
                            shape = RoundedCornerShape(radius)
                    )
                    .background(
                            color = if (enabled) contentColor else MaterialTheme.colorScheme.inverseSurface,
                            shape = RoundedCornerShape(radius)
                    )
                    .border(
                            width = MaterialTheme.spacing.spaceDoubleDp * 3,
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(radius)
                    )
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.spaceDoubleDp * 3),
            colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,

                    disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
            ), enabled = enabled, onClick = onClick
    ) {

        Text(
                text = buttonText,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
        )

    }
}


@PreviewLightDark
@Composable
private fun AppButtonPreview() {

    ScribbleDashTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            AppButton(
                    contentColor = MaterialTheme.colorScheme.primary,
                    onClick = {}
            )

            AppButton(
                    contentColor = Success,
                    onClick = {}
            )
            AppButton(
                    contentColor = Success,
                    enabled = false,
                    onClick = {}
            )
        }
    }
}