package com.example.tradelearn.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
        darkColorScheme(
                primary = PrimaryIndigo,
                secondary = Blue400,
                tertiary = Yellow400,
                background = Slate900,
                surface = Slate800,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onTertiary = Slate900,
                onBackground = Slate50,
                onSurface = Slate50
        )

private val LightColorScheme =
        lightColorScheme(
                primary = PrimaryIndigo,
                secondary = Blue500,
                tertiary = Yellow500,
                background = Slate50,
                surface = Color.White,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onTertiary = Color.White,
                onBackground = Slate900,
                onSurface = Slate900
        )

@Composable
fun TradeLearnTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
) {
    val colorScheme =
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (darkTheme) dynamicDarkColorScheme(context)
                    else dynamicLightColorScheme(context)
                }
                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
