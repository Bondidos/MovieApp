package com.bondidos.utils

import kotlinx.datetime.*
import kotlin.time.Duration.Companion.hours

object DateUtils {
    fun isOlderThenNowWithGivenGap(timeStamp: Long?, gap: Int): Boolean {
        if (timeStamp == null) return false
        val dateToCompare = Instant.fromEpochMilliseconds(timeStamp)
        val dateCompareWith = Clock.System.now() - gap.hours
        return dateToCompare >= dateCompareWith
    }
}