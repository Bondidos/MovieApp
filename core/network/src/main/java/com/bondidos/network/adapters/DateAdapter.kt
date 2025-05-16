package com.bondidos.network.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DateAdapter : JsonAdapter<Date>() {
    private val formats = listOf(
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        },
        SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    )

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateString = reader.nextString()
            parseDate(dateString)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseDate(dateString: String): Date? {
        formats.forEach { format ->
            try {
                return format.parse(dateString)
            } catch (e: Exception) {
                // Try next format
            }
        }
        return null
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        value?.let {
            writer.value(formats[0].format(it))
        }
    }
}