package com.tonyxlab.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.abs

 fun DrawScope.drawCustomPaths(
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
fun List<Offset>.centerAndScaleToFit(canvasSize: Size): List<Offset> {
    if (isEmpty()) return emptyList()

    // Step 1: Determine the user path's bounding box (like a viewport)
    val minX = minOf { it.x }
    val maxX = maxOf { it.x }
    val minY = minOf { it.y }
    val maxY = maxOf { it.y }

    val viewportWidth = maxX - minX
    val viewportHeight = maxY - minY

    if (viewportWidth == 0f || viewportHeight == 0f) return this // prevent divide by zero

    // Step 2: Compute scale factor like in SVG vector logic
    val scaleX = canvasSize.width / viewportWidth
    val scaleY = canvasSize.height / viewportHeight
    val scale = minOf(scaleX, scaleY)

    val scaledWidth = viewportWidth * scale
    val scaledHeight = viewportHeight * scale

    // Step 3: Translate to center (like SVG logic)
    val translateX = (canvasSize.width - scaledWidth) / 2f
    val translateY = (canvasSize.height - scaledHeight) / 2f

    // Step 4: Apply normalization (move to origin), scale, and then translate
    return map { point ->
        val normalizedX = (point.x - minX) * scale + translateX
        val normalizedY = (point.y - minY) * scale + translateY
        Offset(normalizedX, normalizedY)
    }
}


/*fun List<Offset>.centerAndScaleToFit(canvasSize: Size): List<Offset> {
    if (isEmpty()) return emptyList()

    // Compute the bounding box of the drawn path
    val minX = minOf { it.x }
    val maxX = maxOf { it.x }
    val minY = minOf { it.y }
    val maxY = maxOf { it.y }

    val shapeWidth = maxX - minX
    val shapeHeight = maxY - minY

    if (shapeWidth == 0f || shapeHeight == 0f) return this // avoid divide-by-zero

    // Scale to fit within canvas while preserving aspect ratio
    val scale = minOf(
            canvasSize.width / shapeWidth,
            canvasSize.height / shapeHeight
    )

    // Calculate translation to center after scaling
    val offsetX = (canvasSize.width - shapeWidth * scale) / 2f
    val offsetY = (canvasSize.height - shapeHeight * scale) / 2f

    // Normalize the path to (0,0), scale, and then center
    return this.map { point ->
        val normalizedX = (point.x - minX) * scale + offsetX
        val normalizedY = (point.y - minY) * scale + offsetY
        Offset(normalizedX, normalizedY)
    }
}*/

