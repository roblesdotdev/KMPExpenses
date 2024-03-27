package core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ui.theme.DarkColorScheme
import ui.theme.LightColorScheme
import ui.theme.Typography

@Composable
actual fun AppTheme(isDark: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content,
    )

}