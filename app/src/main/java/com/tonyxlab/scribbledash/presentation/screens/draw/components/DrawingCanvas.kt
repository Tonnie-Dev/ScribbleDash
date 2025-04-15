package com.tonyxlab.scribbledash.presentation.screens.draw.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastForEach
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.PathData
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawingActionEvent
import com.tonyxlab.scribbledash.presentation.theme.OnSurface
import kotlin.math.abs

@Composable
fun DrawingCanvas(
    currentPath: PathData?,
    paths: List<PathData>,
    onAction: (DrawingActionEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier,
            shape = RoundedCornerShape(MaterialTheme.spacing.spaceTwelve * 2),
            elevation = CardDefaults.cardElevation(
                    defaultElevation = MaterialTheme.spacing.spaceDoubleDp * 3
            ),
            colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
            )
    ) {
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(MaterialTheme.spacing.spaceMedium)
                        .border(
                                width = MaterialTheme.spacing.spaceHalfDp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = RoundedCornerShape(MaterialTheme.spacing.spaceTwelve * 2)
                        ),
                contentAlignment = Alignment.Center
        ) {

            Canvas(
                    modifier = Modifier
                            .fillMaxSize()
                            .clipToBounds()
                            .background(color = MaterialTheme.colorScheme.surface)
                            .pointerInput(true) {
                                detectDragGestures(
                                        onDragStart = {
                                            onAction(DrawingActionEvent.OnStartNewPath)
                                        },

                                        onDrag = { change, _ ->
                                            onAction(DrawingActionEvent.OnDraw(change.position))

                                        },
                                        onDragEnd = {
                                            onAction(DrawingActionEvent.OnEndPath)
                                        },

                                        onDragCancel = {

                                            onAction(DrawingActionEvent.OnEndPath)

                                        }
                                )


                            }

            ) {

                drawGridLines()

                paths.fastForEach { pathData ->
                    drawPath(path = pathData.path, color = pathData.color)
                }

                currentPath?.let {
                    drawPath(path = it.path, color = it.color)
                }
            }
        }
    }
}

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

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color = Color.Black,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {

            moveTo(path.first().x, path.first().y)

            val smoothness = 5

            for (i in 1..path.lastIndex) {

                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x.minus(to.x))
                val dy = abs(from.y.minus(to.y))

                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                            x1 = (from.x + to.x) / 2f,
                            y1 = (from.y + to.y) / 2f,
                            x2 = to.x,
                            y2 = to.y
                    )

                }

            }
        }

    }
    drawPath(
            path = smoothedPath,
            color = color,
            style = Stroke(
                    width = thickness,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
            )
    )
}
