package com.tonyxlab.scribbledash.domain.json

import kotlinx.serialization.KSerializer

interface JsonSerializer {
    fun <T> toJson(
        serializer: KSerializer<T>,
        data: T,
    ): String

    fun <T> fromJson(
        serializer: KSerializer<T>,
        json: String,
    ): T
}