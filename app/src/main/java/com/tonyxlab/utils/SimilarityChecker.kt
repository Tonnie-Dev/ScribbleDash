package com.tonyxlab.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PathMeasure
import android.graphics.PorterDuff
import android.graphics.RectF
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.vector.PathParser
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get

fun calculatePathSimilarity(
    referencePathStrings: List<String>,
    userPathStrings: List<String>,
    difficulty: DifficultyLevel,
    canvasSize: Size,
    userStrokeWidth: Float = 10f
): Int {
    val strokeMultiplier = when (difficulty) {
        DifficultyLevel.BEGINNER -> 15f
        DifficultyLevel.CHALLENGING -> 7f
        DifficultyLevel.MASTER -> 4f
    }

    val referenceStrokeWidth = userStrokeWidth * strokeMultiplier

    val referencePaths = referencePathStrings.map {
        PathParser().parsePathString(it).toPath().asAndroidPath()
    }

    val userPaths = userPathStrings.map {
        PathParser().parsePathString(it).toPath().asAndroidPath()
    }

    val normalizedReferenceBitmap = createNormalizedBitmapWithMatrix(
            paths = referencePaths,
            canvasSize = canvasSize,
            strokeWidth = referenceStrokeWidth
    )

    val normalizedUserBitmap = createNormalizedBitmapWithMatrix(
            paths = userPaths,
            canvasSize = canvasSize,
            strokeWidth = userStrokeWidth,
            referenceStrokeWidth = referenceStrokeWidth
    )

    val coverage = compareBitmaps(normalizedUserBitmap, normalizedReferenceBitmap) * 100f

    val referenceLength = referencePaths.sumOf { getPathLength(it).toDouble() }
    val userLength = userPaths.sumOf { getPathLength(it).toDouble() }

   val lengthRatio = if (referenceLength > 0) userLength / referenceLength else 0.0
    val lengthPenalty = if (lengthRatio < 0.7) {
        100.0 - (lengthRatio * 100.0)
    } else 0.0

    val finalScore = (coverage - lengthPenalty).coerceIn(0.0, 100.0)
    return finalScore.toInt()
}



private fun createNormalizedBitmapWithMatrix(
    paths: List<android.graphics.Path>,
    canvasSize: Size,
    strokeWidth: Float,
    referenceStrokeWidth: Float = 0f
): Bitmap {
    val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
        this.strokeWidth = strokeWidth
    }

    val bounds = RectF().also { rect ->
        val combined = android.graphics.Path().apply {
            paths.forEach { addPath(it) }
        }
        combined.computeBounds(rect, true)
    }

    // Inset for stroke width difference
    val halfStroke = strokeWidth / 2f
    val insetExtra = maxOf((referenceStrokeWidth - strokeWidth) / 2f, 0f)
    bounds.inset(-halfStroke - insetExtra, -halfStroke - insetExtra)

    val scale = minOf(
            canvasSize.width / bounds.width(),
            canvasSize.height / bounds.height()
    )

    val dx = (canvasSize.width - bounds.width() * scale) / 2f - bounds.left * scale
    val dy = (canvasSize.height - bounds.height() * scale) / 2f - bounds.top * scale

    val transform = Matrix().apply {
        postScale(scale, scale)
        postTranslate(dx, dy)
    }

    val bitmap = createBitmap(canvasSize.width.toInt(), canvasSize.height.toInt())
    val canvas = Canvas(bitmap).apply {
        drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    }

    paths.forEach { path ->
        val transformed = android.graphics.Path(path).apply {
            transform(transform)
        }
        canvas.drawPath(transformed, paint)
    }

    return bitmap
}


private fun compareBitmaps(userBitmap: Bitmap, referenceBitmap: Bitmap): Float {
    var visibleUserPixelCount = 0
    var matchedUserPixelCount = 0

    for (x in 0 until userBitmap.width) {
        for (y in 0 until userBitmap.height) {
            val userPixel = userBitmap[x, y]
            val referencePixel = referenceBitmap[x, y]

            val userVisible = userPixel != Color.TRANSPARENT
            val refVisible = referencePixel != Color.TRANSPARENT

            if (userVisible) {
                visibleUserPixelCount++
                if (refVisible) matchedUserPixelCount++
            }
        }
    }

    return if (visibleUserPixelCount == 0) 0f
    else matchedUserPixelCount.toFloat() / visibleUserPixelCount.toFloat()
}

private fun getPathLength(path: android.graphics.Path): Float {
    var totalLength = 0f
    val pathMeasure = PathMeasure(path, false)

    do {
        totalLength += pathMeasure.length
    } while (pathMeasure.nextContour())

    return totalLength
}




enum class DifficultyLevel {
    BEGINNER,
    CHALLENGING,
    MASTER
}


