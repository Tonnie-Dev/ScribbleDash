package com.tonyxlab.util

import android.R.attr.pathData
import android.content.Context
import android.graphics.drawable.VectorDrawable
import androidx.core.content.res.ResourcesCompat
import org.xmlpull.v1.XmlPullParser
import kotlin.jvm.java
import com.tonyxlab.scribbledash.R
import timber.log.Timber

fun getDrawablePathDataPerVector(context: Context): Map<String, VectorPathData> {
    val resultMap = mutableMapOf<String, VectorPathData>()
    val fields = R.drawable::class.java.fields

    for (field in fields) {
        val pathDataList = mutableListOf<String>()
        var viewportWidth = 0f
        var viewportHeight = 0f

        try {
            val drawableId = field.getInt(null)
            val parser = context.resources.getXml(drawableId)
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    when (parser.name) {
                        "vector" -> {
                            viewportWidth = parser.getAttributeValue(
                                    "http://schemas.android.com/apk/res/android",
                                    "viewportWidth"
                            )?.toFloatOrNull() ?: 0f

                            viewportHeight = parser.getAttributeValue(
                                    "http://schemas.android.com/apk/res/android",
                                    "viewportHeight"
                            )?.toFloatOrNull() ?: 0f
                        }

                        "path" -> {
                            val pathData = parser.getAttributeValue(
                                    "http://schemas.android.com/apk/res/android",
                                    "pathData"
                            )
                            if (!pathData.isNullOrBlank()) {
                                pathDataList.add(pathData)
                            }
                        }
                    }
                }
                eventType = parser.next()
            }

            if (pathDataList.isNotEmpty() && viewportWidth > 0 && viewportHeight > 0) {
                resultMap[field.name] = VectorPathData(pathDataList, viewportWidth, viewportHeight)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse drawable: ${field.name}")
        }
    }

    return resultMap
}


data class VectorPathData(
    val pathDataList: List<String>,
    val viewportWidth: Float,
    val viewportHeight: Float
)
