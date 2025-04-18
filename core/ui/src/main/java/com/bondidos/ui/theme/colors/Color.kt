package com.bondidos.core_ui.theme.colors

import androidx.compose.ui.graphics.Color

interface AppColors {
    val appBackground: Color
    val appSplashLight: Color
    val appSplashDark: Color
    val buttonActive: Color
    val starActive: Color
    val mainText: Color
    val additionalText: Color
    val attentionText: Color
    val textInputBorder: Color
    val activeButtonColor: Color
    val onButtonText: Color
    val navigationBarIcon: Color
    val navigationItemSelected: Color
    val textFieldBackGround: Color

    companion object {
        private val dark = AppColorsDark()
        private val light = AppColorsLight()

        fun get(darkTheme: Boolean): AppColors = if (darkTheme) dark else light
    }

}

private data class AppColorsLight(
    override val appBackground: Color = Color(0xFFFFFFFF),
    override val appSplashLight: Color = Color(0xFFDC474D),
    override val appSplashDark: Color = Color(0xFFDD454C),
    override val buttonActive: Color = Color(0xFFE51937),
    override val starActive: Color = Color(0xFFF8C42F),
    override val mainText: Color = Color(0xFF0F1B2B),
    override val additionalText: Color = Color(0xFF6C737E),
    override val attentionText: Color = Color(0xFFE51937),
    override val textInputBorder: Color = Color(0x330F1B2B),
    override val activeButtonColor: Color = Color(0xFFE51937),
    override val onButtonText: Color = Color(0xFFFFFFFF),
    override val navigationBarIcon: Color = Color(0xFF0F1B2B),
    override val navigationItemSelected: Color = Color(0xFF47CFFF),
    override val textFieldBackGround: Color = Color(0xFFFFFFFF),
) : AppColors

private data class AppColorsDark(
    override val appBackground: Color = Color(0xFF0F1B2B),
    override val appSplashLight: Color = Color(0xFFDC474D),
    override val appSplashDark: Color = Color(0xFFDD454C),
    override val buttonActive: Color = Color(0xFFE51937),
    override val starActive: Color = Color(0xFFF8C42F),
    override val mainText: Color = Color(0xFFFFFFFF),
    override val additionalText: Color = Color(0xFFBFC2C7),
    override val attentionText: Color = Color(0xFFE51937),
    override val textInputBorder: Color = Color(0xFF2B3543),
    override val activeButtonColor: Color = Color(0xFFE51937),
    override val onButtonText: Color = Color(0xFFFFFFFF),
    override val navigationBarIcon: Color = Color(0xFFFFFFFF),
    override val navigationItemSelected: Color = Color(0xFF47CFFF),
    override val textFieldBackGround: Color = Color(0xFF2B3543),
) : AppColors
