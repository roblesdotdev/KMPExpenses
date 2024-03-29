package navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
        initialRoute = Destination.Home.route,
    ) {
        scene(Destination.Home.route) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState = uiState) { expense ->
                navigator.navigate(Destination.EditCreate.withArgs("${expense.id}"))
            }
        }

        scene(Destination.EditCreate.route + "/{id}?") {backstackEntry ->
            val idFromPath: Long? = backstackEntry.path<Long>("id")
            if (idFromPath != null) {
                Text( text = "Detail with id $idFromPath")
            } else {
                Text(text = "Create new expense")
            }
        }
    }
}