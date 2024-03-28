package expenses.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import expenses.domain.model.Expense

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesScreen(uiState: ExpensesUIState) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            item {
                ExpensesTotalHeader(total = uiState.total)
            }
            stickyHeader {
                AllExpensesHeader()
            }
            items(uiState.expenses) { expense ->
                ExpenseItem(expense, onExpenseClick = {})
            }
        }
    }
}

@Composable
fun ExpensesTotalHeader(total: Double) {
    Card(
        shape = RoundedCornerShape(30),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(130.dp).padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = "$$total", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "USD",
                modifier = Modifier.align(Alignment.CenterEnd),
                color = MaterialTheme.colorScheme.surfaceTint
            )
        }
    }
}

@Composable
fun AllExpensesHeader() {
    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "All Expenses",
            style = MaterialTheme.typography.titleMedium
        )
        OutlinedButton(
            shape = RoundedCornerShape(50),
            onClick = {},
        ) {
            Text("View all")
        }
    }

}

@Composable
fun ExpenseItem(expense: Expense, onExpenseClick: (expense: Expense) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            onExpenseClick(expense)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(35),
                color = MaterialTheme.colorScheme.primary,
            ) {
                Image(
                    imageVector = expense.icon,
                    modifier = Modifier.padding(10.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    contentDescription = null,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.category.name, style = MaterialTheme.typography.titleMedium)
                Text(text = expense.description, style = MaterialTheme.typography.bodyMedium)
            }

            Text(text = "$${expense.amount}", style = MaterialTheme.typography.titleLarge)
        }
    }
}