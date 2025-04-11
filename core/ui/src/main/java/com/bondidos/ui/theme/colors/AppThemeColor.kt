package com.bondidos.core_ui.theme.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bondidos.core_ui.theme.appColors


enum class AppThemeColor {
    APP_BACKGROUND,
    APP_SPLASH,
    BUTTON_ACTIVE,
    STAR_ACTIVE,
    MAIN_TEXT,
    ADDITIONAL_TEXT,
    ATTENTION_TEXT,
    TEXT_INPUT_BORDER,
    ACTIVE_BUTTON,
    ON_BUTTON_TEXT,
    NAVIGATION_BAR_ICON,
    NAVIGATION_ITEM_SELECTED,
    TEXT_FIELD_BACKGROUND;

    @Composable
    fun color(): Color {
        val colors = MaterialTheme.appColors
        return when (this) {
            APP_BACKGROUND -> colors.appBackground
            APP_SPLASH -> colors.appSplash
            BUTTON_ACTIVE -> colors.buttonActive
            STAR_ACTIVE -> colors.starActive
            MAIN_TEXT -> colors.mainText
            ADDITIONAL_TEXT -> colors.additionalText
            ATTENTION_TEXT -> colors.attentionText
            TEXT_INPUT_BORDER -> colors.textInputBorder
            ACTIVE_BUTTON -> colors.activeButtonColor
            ON_BUTTON_TEXT -> colors.onButtonText
            NAVIGATION_BAR_ICON -> colors.navigationBarIcon
            NAVIGATION_ITEM_SELECTED -> colors.navigationItemSelected
            TEXT_FIELD_BACKGROUND -> colors.textFieldBackGround
        }
    }
}