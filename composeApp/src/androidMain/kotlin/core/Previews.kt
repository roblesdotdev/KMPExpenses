package core

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.presentation.AppTheme
import expenses.presentation.ExpensesScreen

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenLight() {
    AppTheme(isDark = false) {
        ExpensesScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenDark() {
    AppTheme(isDark = true) {
        ExpensesScreen()
    }
}

