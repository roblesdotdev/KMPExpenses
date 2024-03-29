package navigation

sealed class Destination(val route: String) {
    data object Home : Destination("home")
    data object EditCreate : Destination("edit_create")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }

    }
}

enum class TitleTopBarTitles(val value: String) {
    DASHBOARD("Dashboard"),
    EDIT("Edit Expense"),
    CREATE("Add Expense")
}