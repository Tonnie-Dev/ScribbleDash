package com.tonyxlab.scribbledash.presentation.screens.stats.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

@Composable
fun StatItem(
    @DrawableRes icon: Int,
    @StringRes itemText: Int,
    quota: String,
    modifier: Modifier = Modifier
) {
    Surface {
        Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.spacing.spaceTwelve
                )
        ) {


            Image(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceLarge),
                    painter = painterResource(icon),
                    contentDescription = ""
            )
            /*
                        Box(
                                Modifier
                                        .clip(RoundedCornerShape(MaterialTheme.spacing.spaceTwelve))
                                        .background(
                                                color = iconBackgroundColor,
                                                shape = RoundedCornerShape(MaterialTheme.spacing.spaceTwelve)
                                        )
                                        .size(MaterialTheme.spacing.spaceDoubleDp * 26),
                                contentAlignment = Alignment.Center
                        ) {
                        }
            */

            AppBodyText(
                    modifier = Modifier.fillMaxWidth(.5f),
                    text = stringResource(id = itemText),
                    textStyle = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(1f))
            AppHeaderText(
                    text = quota,
                    textStyle = MaterialTheme.typography.headlineLarge
            )

        }

    }
}


@PreviewLightDark
@Composable
private fun StateItemPreview() {
    ScribbleDashTheme {



    Column(
            modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {

        StatItem(
                icon = R.drawable.stat_item_hour_glass,
                itemText = R.string.stat_text_highest_speed_draw,
                quota = "89%"
        )

        StatItem(
                icon = R.drawable.stat_item_bolt,
                itemText = R.string.stat_text_highest_meh,
                quota = "9"
        )


        StatItem(
                icon = R.drawable.stat_item_star,
                itemText = R.string.stat_text_highest_accuracy,
                quota = "100%"
        )


        StatItem(
                icon = R.drawable.stat_item_palette,
                itemText = R.string.stat_text_highest_completed,
                quota = "13"
        )
    }
    }
}