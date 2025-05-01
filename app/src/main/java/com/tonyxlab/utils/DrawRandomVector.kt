package com.tonyxlab.utils

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.PathParser
import kotlin.collections.component1
import kotlin.collections.component2

fun DrawScope.drawRandomVector(
    vectorPaths: List<String>,
    viewportWidth: Float =0f,
    viewportHeight: Float = 0f
) {
    if (vectorPaths.isEmpty() || viewportWidth == 0f || viewportHeight == 0f) return

    val scaleX = size.width / viewportWidth
    val scaleY = size.height / viewportHeight
    val scale = minOf(scaleX, scaleY)

    val scaledWidth = viewportWidth * scale
    val scaledHeight = viewportHeight * scale

    val translateX = (size.width - scaledWidth) / 2f
    val translateY = (size.height - scaledHeight) / 2f

    withTransform({
        translate(left = translateX, top = translateY)
        scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
    }) {
        vectorPaths.forEach { pathData ->
            val path = PathParser()
                    .parsePathString(pathData)
                    .toPath()

            drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 1f) // stroke-only style
            )
        }
    }
}


/*
fun DrawScope.drawRandomVector(context: Context) {

    //get a map of drawable name to its vector data
    val allVectors = getRawVectorPathData(context)
    if (allVectors.isEmpty()) return

    //deconstruct and pick random drawable
    val (name, vectorData) = allVectors.entries.random()
    val (paths, vpWidth, vpHeight) = vectorData

    if (vpWidth == 0f || vpHeight == 0f || paths.isEmpty()) return

    //calculate scaling factor
    val scaleX = size.width / vpWidth
    val scaleY = size.height / vpHeight
    val scale = minOf(scaleX, scaleY)

    //scale Width and Height

    val scaledWidth = vectorData.viewportWidth * scale
    val scaledHeight = vectorData.viewportHeight * scale

    val translateX = (size.width - scaledWidth) / 2f
    val translateY = (size.width - scaledHeight) / 2f


    withTransform({
        translate(left = translateX, top = translateY)
        scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
    }) {
        paths.forEach { pathData ->
            val path = PathParser().parsePathString(pathData)
                    .toPath()

            drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 1f) // stroke-only style
            )
        }
    }
}
*/
