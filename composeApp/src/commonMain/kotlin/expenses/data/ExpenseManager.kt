package expenses.data

import expenses.domain.model.Expense
import expenses.domain.model.ExpenseCategory

object ExpenseManager {
    val fakeExpenses = (1..10).map {
        Expense(
            id = it.toLong(),
            amount = 1233.22,
            category = ExpenseCategory.GROCERIES,
            description = "Item $it"
        )
    }
}
