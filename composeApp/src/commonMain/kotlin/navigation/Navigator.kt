package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import expenses.data.ExpenseManager
import expenses.domain.repo.ExpenseRepoImpl
import expenses.presentation.ExpensesScreen
import expenses.presentation.ExpensesViewModel
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun Navigation(modifier: Modifier = Modifier, navigator: Navigator) {
    val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
        ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
    }

    NavHost(
        modifier = modifier,
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene("/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState = uiState) { expense ->
                navigator.navigate("/addExpenses/${expense.id}")
            }
        }

        scene("/addExpenses/{id}") {backstackEntry ->
            val idFromPath = backstackEntry.path<Long>("id")
            Text( text = "Detail with id $idFromPath")
        }
    }
}