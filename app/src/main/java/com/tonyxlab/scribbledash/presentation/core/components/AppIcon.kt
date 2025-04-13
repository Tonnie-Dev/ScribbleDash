package com.tonyxlab.scribbledash.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.core.utils.spacing

@Composable
fun AppIcon(
    @DrawableRes icon: Int,
    backgroundColor: Color = MaterialTheme.colorScheme.inverseSurface,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onBackgroundColor: Color = MaterialTheme.colorScheme.onBackground,
    radius: Dp = MaterialTheme.spacing.spaceDoubleDp * 11,
    onClick: () -> Unit
) {
    IconButton(
            modifier = modifier
                    .clip(RoundedCornerShape(radius))
                    .size(MaterialTheme.spacing.spaceExtraLarge)
                    .background(
                            color = if (enabled) backgroundColor
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .4f),
                            shape = RoundedCornerShape(radius)
                    ),
            onClick = onClick
    ) {
        Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = if (enabled) onBackgroundColor else onBackgroundColor.copy(alpha = .5f)
        )
    }
}

@PreviewLightDark
@Composable
private fun AppIconPreview() {
    ScribbleDashTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            AppIcon(
                    icon = R.drawable.ic_reply,
                    onClick = {},
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            )
            AppIcon(
                    icon = R.drawable.ic_reply,
                    onClick = {},

                    )

            AppIcon(
                    icon = R.drawable.ic_reply,
                    onClick = {},

                    )

            AppIcon(
                    enabled = false,
                    icon = R.drawable.ic_reply,
                    onClick = {},

                    )
        }
    }
}
