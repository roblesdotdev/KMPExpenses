package expenses.domain.repo

import expenses.domain.model.Expense
import expenses.domain.model.ExpenseCategory

interface ExpenseRepo {
    fun getAllExpenses(): List<Expense>

    fun addExpense(expense: Expense)

    fun editExpense(expense: Expense)

    fun getCategories(): List<ExpenseCategory>
}