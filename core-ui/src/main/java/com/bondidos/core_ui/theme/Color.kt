package com.bondidos.core_ui.theme

import androidx.compose.ui.graphics.Color

interface AppColors {
    val appBackground: Color
    val appSplash: Color
    val buttonActive: Color
    val starActive: Color
    val mainText: Color
    val additionalText: Color
    val attentionText: Color
}

data class AppColorsLight(
    override val appBackground: Color = Color(0xFFFFFFFF),
    override val appSplash: Color = Color(0xFFDD454C),
    override val buttonActive: Color = Color(0xFFE51937),
    override val starActive: Color = Color(0xFFF8C42F),
    override val mainText: Color = Color(0xFF0F1B2B),
    override val additionalText: Color = Color(0xFF6C737E),
    override val attentionText: Color = Color(0xFFE51937),
) : AppColors

data class AppColorsDark(
    override val appBackground: Color = Color(0xFF0F1B2B),
    override val appSplash: Color = Color(0xFFDC474D),
    override val buttonActive: Color = Color(0xFFE51937),
    override val starActive: Color =Color(0xFFF8C42F),
    override val mainText: Color = Color(0xFFFFFFFF),
    override val additionalText: Color = Color(0xFFBFC2C7),
    override val attentionText: Color = Color(0xFFE51937)
) : AppColors
