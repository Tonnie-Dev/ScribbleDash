package com.tonyxlab.scribbledash.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppColorScheme = lightColorScheme(
        primary = Primary,
        onPrimary = OnPrimary,
        secondary = Secondary,
        tertiary = TertiaryContainer,
        error = Error,
        background = Background,
        onBackground = OnBackground,
        surface = SurfaceHigh,
        onSurface = OnSurface,
        surfaceVariant = SurfaceLow,
        onSurfaceVariant = OnSurfaceVar,
        inverseSurface = SurfaceLowest
)

@Composable
fun ScribbleDashTheme(content: @Composable () -> Unit) {
    MaterialTheme(
            colorScheme = AppColorScheme,
            shapes = shapes,
            typography = Typography,
            content = content
    )
}


