import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import core.presentation.AppTheme
import expenses.data.ExpenseManager
import expenses.domain.repo.ExpenseRepoImpl
import expenses.presentation.ExpensesScreen
import expenses.presentation.ExpensesViewModel
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(isDark: Boolean = false) {
    PreComposeApp {
        val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
            ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        AppTheme(isDark = isDark) {
            ExpensesScreen(uiState = state)
        }
    }
}