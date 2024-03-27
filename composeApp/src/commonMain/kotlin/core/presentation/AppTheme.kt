package core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun AppTheme(isDark: Boolean, content: @Composable () -> Unit)