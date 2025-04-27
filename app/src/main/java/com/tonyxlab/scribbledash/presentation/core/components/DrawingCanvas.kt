package com.tonyxlab.scribbledash.presentation.core.components

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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawingActionEvent
import com.tonyxlab.utils.drawGridLines
import com.tonyxlab.utils.thenIf

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    onAction: ((DrawingActionEvent) -> Unit)? = null,
    canDraw: Boolean = false,
    onCustomDraw: (DrawScope.() -> Unit)? = null
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
                                                onAction?.invoke(DrawingActionEvent.OnStartNewPath)

                                            },
                                            onDrag = { change, _ ->
                                                onAction?.invoke(DrawingActionEvent.OnDraw(change.position))

                                            },
                                            onDragEnd = {
                                                onAction?.invoke(DrawingActionEvent.OnEndPath)
                                            },
                                            onDragCancel = {
                                                onAction?.invoke(DrawingActionEvent.OnEndPath)
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









