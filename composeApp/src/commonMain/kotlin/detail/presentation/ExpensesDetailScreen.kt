package detail.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import expenses.domain.model.Expense

@Composable
fun ExpensesDetailScreen(expense: Expense? = null, onSaveClick: (Expense) -> Unit) {
    val isEdit = expense != null
    Text(text = if (isEdit) "Editing" else "Creating")
}