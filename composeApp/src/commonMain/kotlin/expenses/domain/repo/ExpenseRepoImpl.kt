package expenses.domain.repo

import expenses.data.ExpenseManager
import expenses.domain.model.Expense
import expenses.domain.model.ExpenseCategory

class ExpenseRepoImpl(private val expenseManager: ExpenseManager): ExpenseRepo {
    override fun getAllExpenses(): List<Expense> {
        return expenseManager.fakeExpenses
    }

    override fun addExpense(expense: Expense) {
        expenseManager.addExpense(expense)
    }

    override fun editExpense(expense: Expense) {
        expenseManager.editExpense(expense)
    }

    override fun getCategories(): List<ExpenseCategory> {
        return expenseManager.getCategories()
    }
}