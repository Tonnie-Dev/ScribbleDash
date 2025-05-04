package com.tonyxlab.utils

import android.content.Context
import android.util.Xml
import androidx.compose.ui.geometry.Offset
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.PathData
import org.xmlpull.v1.XmlPullParser
import timber.log.Timber
import java.io.IOException

fun getRawVectorPathData(context: Context): Map<String, VectorPathData> {
    val resultMap = mutableMapOf<String, VectorPathData>()
    val assetManager = context.assets
    val folder = "vectors"

    try {
        val fileNames = assetManager.list(folder) ?: emptyArray()

        for (fileName in fileNames) {
            if (!fileName.endsWith(".xml")) continue

            val pathList = mutableListOf<String>()
            var vpWidth = 0f
            var vpHeight = 0f

            try {
                val inputStream = assetManager.open("$folder/$fileName")
                val parser = Xml.newPullParser()
                parser.setInput(inputStream, null)

                var eventType = parser.eventType
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        when (parser.name) {
                            "vector" -> {
                                vpWidth = parser.getAttributeValue(
                                        "http://schemas.android.com/apk/res/android",
                                        "viewportWidth"
                                )
                                        ?.toFloatOrNull() ?: 0f

                                vpHeight = parser.getAttributeValue(
                                        "http://schemas.android.com/apk/res/android",
                                        "viewportHeight"
                                )
                                        ?.toFloatOrNull() ?: 0f
                            }

                            "path" -> {
                                val pathData = parser.getAttributeValue(
                                        "http://schemas.android.com/apk/res/android",
                                        "pathData"
                                )
                                if (!pathData.isNullOrBlank()) {
                                    pathList.add(pathData)
                                }
                            }
                        }
                    }
                    eventType = parser.next()
                }

                val key = fileName.removeSuffix(".xml")
                if (pathList.isNotEmpty()) {
                    resultMap[key] = VectorPathData(pathList, vpWidth, vpHeight)
                }

                inputStream.close()

            } catch (e: Exception) {
                Timber.e(e, "Failed to parse vector file: $fileName")
            }
        }

    } catch (e: IOException) {
        Timber.e(e, "Failed to list vector assets")
    }

    return resultMap
}

data class VectorPathData(
    val pathDataList: List<String>,
    val viewportWidth: Float,
    val viewportHeight: Float
)




/**
 * Extension function to convert a list of `PathData` objects to a list of SVG path strings.
 *
 * Each `PathData` object is expected to contain a list of `PointF` representing a path.
 * This function generates an SVG path string for each `PathData` and returns a list of these strings.
 * Empty paths are ignored.
 */
fun List<PathData>.toSvgPathStrings(): List<String> {
    return this.mapNotNull { pathData ->
        pathData.toSvgPathString()
    }
}

/**
 * Extension function to convert a single PathData to an SVG path string.
 * Returns null if the path is empty.
 */
private fun PathData.toSvgPathString(): String? {
    if (path.isEmpty()) {
        return null
    }

    return buildString {
        // Use a StringBuilder for efficient string building.
        // Start with the "Move To" command using the first point.
        append("M ${path.first().x} ${path.first().y} ")

        // Use a loop to append "Line To" commands for the remaining points.
        path.drop(1).forEach { point ->
            append("L ${point.x} ${point.y} ")
        }
    }.trim() // Remove any trailing whitespace.
}


/*

fun List<String>.toOffsetPaths(): List<List<Offset>> {
    return this.mapNotNull { pathString ->
        val tokens = pathString.trim().split("\\s+".toRegex())
        if (tokens.size < 3) return@mapNotNull null

        val offsets = mutableListOf<Offset>()
        var i = 0

        while (i < tokens.size) {
            val cmd = tokens[i]
            if (cmd == "M" || cmd == "L") {
                if (i + 2 < tokens.size) {
                    val x = tokens[i + 1].toFloatOrNull()
                    val y = tokens[i + 2].toFloatOrNull()
                    if (x != null && y != null) {
                        offsets.add(Offset(x, y))
                        i += 3
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else {
                i++
            }
        }

        if (offsets.isNotEmpty()) offsets else null
    }

}

*/

/**
 * Converts a list of strings to a list of lists of Offset objects.
 *
 * Each string in the list is expected to represent a sequence of path commands and coordinates,
 * such as "M x1 y1 L x2 y2 L x3 y3 ...". This function parses each string,
 * extracting the coordinates defined by the "M" (move to) and "L" (line to) commands,
 * and represents them as Offset objects.
 *
 * @receiver The list of strings to convert.
 * @return A list of lists of Offset objects, where each inner list represents a path
 *   and each Offset object represents a point on that path. Returns an empty list if the input
 *   list is empty or if no valid paths can be parsed.
 */
fun List<String>.toOffsetPaths(): List<List<Offset>> {
    return mapNotNull { pathString ->
        pathString.toOffsetPath()
    }.filter { it.isNotEmpty() }
}

/**
 * Converts a single path string to a list of Offset objects.
 *
 * This function parses a path string, extracting coordinates for "M" (move to) and "L" (line to)
 * commands and representing them as Offset objects. Other commands are ignored.
 *
 * @receiver The path string to parse.
 * @return A list of Offset objects representing the points on the path, or null if the
 *   path string is invalid or does not contain any valid "M" or "L" commands.
 */
private fun String.toOffsetPath(): List<Offset>? {
    val tokens = this.trim()
            .split("\\s+".toRegex())

    // Validate minimum token size for a valid path (e.g., "M x y").
    if (tokens.size < 3) return null

    val offsets = mutableListOf<Offset>()
    var currentIndex = 0

    while (currentIndex < tokens.size) {
        val command = tokens[currentIndex]

        when (command) {
            "M", "L" -> {
                // Ensure there are enough tokens for x and y coordinates.
                if (currentIndex + 2 >= tokens.size) return null

                val x = tokens[currentIndex + 1].toFloatOrNull()
                val y = tokens[currentIndex + 2].toFloatOrNull()

                // Validate coordinates.
                if (x == null || y == null) return null

                offsets.add(Offset(x, y))
                currentIndex += 3 // Move to the next command.
            }

            else -> {
                currentIndex++ // Ignore unknown commands.
            }
        }
    }

    return offsets
}

