package com.tonyxlab.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.abs

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
