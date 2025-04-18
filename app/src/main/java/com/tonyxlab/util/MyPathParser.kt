package com.tonyxlab.util

import android.R.attr.pathData
import android.content.Context
import android.graphics.drawable.VectorDrawable
import androidx.core.content.res.ResourcesCompat
import org.xmlpull.v1.XmlPullParser
import kotlin.jvm.java
import com.tonyxlab.scribbledash.R
import timber.log.Timber

fun getDrawablePathDataPerVector(context: Context): Map<String, List<String>> {
    val resultMap = mutableMapOf<String, List<String>>()
    val fields = R.drawable::class.java.fields
    Timber.i("Drawable resource count: ${fields.size}")

    for (field in fields) {
        val pathDataList = mutableListOf<String>()
        try {
            val drawableId = field.getInt(null)
            val parser = context.resources.getXml(drawableId)
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
                    val pathData = parser.getAttributeValue(
                            "http://schemas.android.com/apk/res/android",
                            "pathData"
                    )
                    if (pathData != null) {
                        pathDataList.add(pathData)
                    }
                }
                eventType = parser.next()
            }

            if (pathDataList.isNotEmpty()) {
                val drawableName = field.name
                resultMap[drawableName] = pathDataList
                Timber.i("Drawable [$drawableName] has ${pathDataList.size} path(s)")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse drawable: ${field.name}")
        }
    }

    return resultMap
}