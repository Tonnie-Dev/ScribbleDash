package com.tonyxlab.scribbledash.presentation.core.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object GradientScheme {
    val backgroundGradient = Brush.sweepGradient(
            colors = listOf(Color(0xFFFEFAF6), Color(0xFFFFF1E2)),
            center = Offset(.5f, .5f)
    )
}

val LocalGradient = staticCompositionLocalOf { GradientScheme }

val MaterialTheme.gradient
    @Composable @ReadOnlyComposable
    get() = LocalGradient.current