package com.bondidos.analytics

import android.os.Bundle
import androidx.core.os.bundleOf
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class AppAnalytics @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) {
    private fun logEvent(name: String, params: Bundle) {
        firebaseAnalytics.logEvent(name, params)
    }

    fun setUserId(id: String?) = firebaseAnalytics.setUserId(id)

    private fun createButtonParams(buttonName: ButtonNames) = bundleOf(
        "ButtonName" to buttonName.toString(),
    )

    private fun createScreenParams(screenName: ScreenNames) = bundleOf(
        "screenName" to screenName.toString()
    )

    private fun createValidationParameters(validationErrors: List<String>) = bundleOf(
        "errors" to validationErrors.joinToString("\n")
    )

    fun logScreen(screen: ScreenNames) = logEvent("Screen_loaded", createScreenParams(screen))

    fun logButton(button: ButtonNames) = logEvent("Button_clicked", createButtonParams(button))

    fun logValidation(validationErrors: List<String>) =
        logEvent("Validation_errors", createValidationParameters(validationErrors))
}