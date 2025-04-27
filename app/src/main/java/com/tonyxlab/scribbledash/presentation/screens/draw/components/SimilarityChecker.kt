package com.tonyxlab.scribbledash.presentation.screens.draw.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.core.graphics.createBitmap
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation

fun calculatePathSimilarity(
    referencePaths: List<Path>,
    userPaths: List<Path>,
    difficulty: DifficultyLevel,
    canvasSize: Size,
    userStrokeWidth: Float = 10f // default from your drawCustomPaths
): Float {
    val strokeMultiplier = when (difficulty) {
        DifficultyLevel.BEGINNER -> 15f
        DifficultyLevel.CHALLENGING -> 7f
        DifficultyLevel.MASTER -> 4f
    }

    val referenceStrokeWidth = userStrokeWidth * strokeMultiplier

    val normalizedReferenceBitmap = createNormalizedBitmap(
            paths = referencePaths,
            canvasSize = canvasSize,
            strokeWidth = referenceStrokeWidth,
            userStrokeWidth = userStrokeWidth,
            isUser = false
    )

    val normalizedUserBitmap = createNormalizedBitmap(
            paths = userPaths,
            canvasSize = canvasSize,
            strokeWidth = userStrokeWidth,
            userStrokeWidth = userStrokeWidth,
            isUser = true,
            referenceStrokeWidth = referenceStrokeWidth
    )

    return compareBitmaps(normalizedUserBitmap, normalizedReferenceBitmap)
}

private fun createNormalizedBitmap(
    paths: List<Path>,
    canvasSize: Size,
    strokeWidth: Float,
    userStrokeWidth: Float,
    isUser: Boolean,
    referenceStrokeWidth: Float = 0f
): Bitmap {
    val paint = Paint().apply {
        color = android.graphics.Color.BLACK
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
        this.strokeWidth = strokeWidth
    }

    // First: calculate total bounds
    val combinedPath = android.graphics.Path().apply {
        paths.forEach { addPath(it.asAndroidPath()) }
    }
    val bounds = RectF().also { combinedPath.computeBounds(it, true) }

    // Inset bounds
    val halfStroke = strokeWidth / 2f
    val insetExtra = if (isUser) (referenceStrokeWidth - userStrokeWidth) / 2f else 0f
    bounds.inset(halfStroke + insetExtra, halfStroke + insetExtra)

    val shapeWidth = bounds.width()
    val shapeHeight = bounds.height()

    // Calculate scaling (preserving aspect ratio)
    val scale = minOf(canvasSize.width / shapeWidth, canvasSize.height / shapeHeight)

    val bitmap = createBitmap(canvasSize.width.toInt(), canvasSize.height.toInt())
    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

    canvas.withTranslation(-bounds.left, -bounds.top) {
        withScale(scale, scale, 0f, 0f) {
            paths.forEach { canvas.drawPath(it.asAndroidPath(), paint) }
        }
    }

    return bitmap
}

private fun compareBitmaps(userBitmap: Bitmap, referenceBitmap: Bitmap): Float {
    var visibleUserPixelCount = 0
    var matchedUserPixelCount = 0

    for (x in 0 until userBitmap.width) {
        for (y in 0 until userBitmap.height) {
            val userPixel = userBitmap.getPixel(x, y)
            val referencePixel = referenceBitmap.getPixel(x, y)

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

// Utility to convert androidx.compose.ui.graphics.Path to android.graphics.Path
fun Path.toAndroidPath(): android.graphics.Path {
    return this.asAndroidPath()
}

enum class DifficultyLevel {
    BEGINNER,
    CHALLENGING,
    MASTER
}
