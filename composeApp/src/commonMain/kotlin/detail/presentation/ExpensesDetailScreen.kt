package detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import expenses.domain.model.Expense
import expenses.domain.model.ExpenseCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesDetailScreen(expense: Expense? = null, categoryList: List<ExpenseCategory>, onSaveClick: (Expense) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scopeCoroutine = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }

    Text(text = selectedCategory?.name ?: "Select a category", modifier = Modifier.clickable {
        showBottomSheet = true
    })

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            CategoryBottomSheetContent(categories = categoryList, onSelectCategory = {category ->
                selectedCategory = category
                scopeCoroutine.launch { sheetState.hide() }.invokeOnCompletion {
                    showBottomSheet = false
                }
            })
        }
    }
}

@Composable
private fun CategoryBottomSheetContent(
    categories: List<ExpenseCategory>,
    onSelectCategory: (ExpenseCategory) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
    ) {
        items(categories) {
            CategoryItem(category = it, onSelectCategory = onSelectCategory)
        }
    }
}

@Composable
private fun CategoryItem(
    category: ExpenseCategory,
    onSelectCategory: (ExpenseCategory) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            onSelectCategory(category)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(48.dp).clip(CircleShape),
            imageVector = category.icon,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(text = category.name)
    }

}