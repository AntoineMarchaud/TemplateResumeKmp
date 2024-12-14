package com.amarchaud.shared.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.amarchaud.shared.StoreDynamicColor
import org.koin.compose.koinInject

private val primary = Color(0xFF1565C0)
private val secondary = Color(0xFF2196F3)
private val tertiary = Color(0xFFE3F2FD)

private val background = Color(0xFFE3F2FD)
private val white = Color.White

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    onPrimary = white,
    onSecondary = white,
    onTertiary = white,
    background = background,
)

@Composable
fun TemplateResumeKmpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColorClass: StoreDynamicColor = koinInject(),
    content: @Composable () -> Unit
) {
    val wantToDisplayMaterialYou by
    dynamicColorClass.currentMaterialYouDisplay.collectAsState()

    val colorScheme = when {
        dynamicColorClass.hasMaterialYou() && wantToDisplayMaterialYou ->
            dynamicColorClass.getDynamicColorScheme(
                darkTheme = darkTheme
            )

        darkTheme -> LightColorScheme // fallback to light theme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme!!,
        typography = Typography,
        content = content
    )
}