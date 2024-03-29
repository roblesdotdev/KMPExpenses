package core

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.presentation.AppTheme
import expenses.data.ExpenseManager
import expenses.presentation.ExpensesScreen
import expenses.presentation.ExpensesUIState

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenLight() {
    AppTheme(isDark = false) {
        ExpensesScreen(
            uiState = ExpensesUIState(
                expenses = ExpenseManager.fakeExpenses,
                total = 1222.33
            )
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenDark() {
    AppTheme(isDark = true) {
        ExpensesScreen(
            uiState = ExpensesUIState(
                expenses = ExpenseManager.fakeExpenses,
                total = 1222.33
            )
        ) {}
    }
}

