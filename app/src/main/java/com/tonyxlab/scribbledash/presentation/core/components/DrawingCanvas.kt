package com.tonyxlab.scribbledash.presentation.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiEvent
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.utils.drawGridLines
import com.tonyxlab.utils.thenIf

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    canDraw: Boolean = false,
    outerRadius: Dp = MaterialTheme.spacing.spaceDoubleDp * 18,
    paddingValues: PaddingValues = PaddingValues(
            horizontal = MaterialTheme.spacing.spaceTwelve,
            vertical = MaterialTheme.spacing.spaceTwelve
    ),
    onAction: ((DrawUiEvent) -> Unit)? = null,
    onCustomDraw: (DrawScope.() -> Unit)? = null
) {
    Card(
            modifier = modifier,
            shape = RoundedCornerShape(outerRadius),
            elevation = CardDefaults.cardElevation(
                    defaultElevation = MaterialTheme.spacing.spaceDoubleDp * 3
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {

        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(paddingValues)
                        .border(
                                width = MaterialTheme.spacing.spaceHalfDp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                shape = RoundedCornerShape(MaterialTheme.spacing.spaceTwelve * 2)
                        ),
                contentAlignment = Alignment.Center
        ) {

            Canvas(
                    modifier = Modifier
                            .fillMaxSize()
                            .clipToBounds()
                            .background(color = MaterialTheme.colorScheme.surface)
                            .thenIf(canDraw) {

                                pointerInput(true) {

                                    detectDragGestures(
                                            onDragStart = {
                                                onAction?.invoke(DrawUiEvent.OnStartNewPath)

                                            },
                                            onDrag = { change, _ ->
                                                onAction?.invoke(DrawUiEvent.OnDraw(change.position))

                                            },
                                            onDragEnd = {
                                                onAction?.invoke(DrawUiEvent.OnEndPath)
                                            },
                                            onDragCancel = {
                                                onAction?.invoke(DrawUiEvent.OnEndPath)
                                            }
                                    )
                                }
                            }

            ) {
                drawGridLines()
                onCustomDraw?.invoke(this)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DrawingCanvasPreview() {

    ScribbleDashTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            DrawingCanvas(modifier = Modifier.size(MaterialTheme.spacing.spaceMedium * 10))
            DrawingCanvas(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceMedium * 10),
                    paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.spaceExtraSmall),
                    outerRadius = MaterialTheme.spacing.spaceMedium
            )

        }
    }
}









