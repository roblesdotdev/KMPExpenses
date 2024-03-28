import androidx.compose.runtime.Composable
import core.presentation.AppTheme
import expenses.presentation.ExpensesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(isDark: Boolean = false) {
    AppTheme(isDark = isDark) {
        ExpensesScreen()
    }
}