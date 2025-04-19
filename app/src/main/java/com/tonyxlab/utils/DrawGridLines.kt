package com.tonyxlab.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.tonyxlab.scribbledash.presentation.theme.OnSurface

private fun DrawScope.drawGridLines() {

    val canvasWidth = size.width
    val canvasHeight = size.height
    val cellWidth = canvasWidth / 3
    val cellHeight = canvasHeight / 3

    val gridLineColor = OnSurface
    val gridLineWidth = 1.5f

    // Draw 2 horizontal lines
    for (i in 1..2) {
        val y = i * cellHeight
        drawLine(
                color = gridLineColor,
                start = Offset(0f, y),
                end = Offset(canvasWidth, y),
                strokeWidth = gridLineWidth
        )
    }

    // Draw 2j vertical lines
    for (i in 1..2) {
        val x = i * cellWidth
        drawLine(
                color = gridLineColor,
                start = Offset(x, 0f),
                end = Offset(x, canvasHeight),
                strokeWidth = gridLineWidth
        )
    }

}
