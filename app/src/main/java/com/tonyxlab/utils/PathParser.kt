package com.tonyxlab.utils

import android.content.Context
import android.util.Xml
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
