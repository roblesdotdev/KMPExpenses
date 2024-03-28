package expenses.data

import expenses.domain.model.Expense
import expenses.domain.model.ExpenseCategory
import kotlin.math.roundToInt
import kotlin.random.Random

object ExpenseManager {
    val fakeExpenses = (1..10).map {
        Expense(
            id = it.toLong(),
            amount = (Random.nextDouble(100.0, 1000.0) * 100.0).roundToInt() / 100.0,
            category = ExpenseCategory.entries.toTypedArray().random(),
            description = "Description for expense $it"
        )
    }.toMutableList()

    fun addExpense(expense: Expense) {
        fakeExpenses.add(expense.copy(id = (fakeExpenses.size + 1).toLong()))
    }

    fun editExpense(expense: Expense) {
        val idx = fakeExpenses.indexOfFirst { it.id == expense.id }
        if (idx != -1) {
            fakeExpenses[idx] = fakeExpenses[idx].copy(
                amount = expense.amount,
                category = expense.category,
                description = expense.description,
            )
        }
    }

    fun getCategories(): List<ExpenseCategory> {
        return ExpenseCategory.entries
    }
}
