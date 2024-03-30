package detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import expenses.domain.model.Expense
import expenses.domain.model.ExpenseCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesDetailScreen(
    expense: Expense? = null,
    categoryList: List<ExpenseCategory>,
    onSaveClick: (Expense) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scopeCoroutine = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var price by remember { mutableStateOf(expense?.amount ?: 0.0) }
    var selectedCategory by remember {
        mutableStateOf(
            expense?.category
        )
    }
    var description by remember { mutableStateOf(expense?.description ?: "") }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp)) {
        ExpenseAmount(priceContent = price, onPriceChange = { price = it })
        Spacer(modifier = Modifier.height(30.dp))
        ExpenseTypeSelector(selectedCategory = selectedCategory, openBottomSheet = {
            showBottomSheet = true
            scopeCoroutine.launch {
                sheetState.show()
            }
        })
        Spacer(modifier = Modifier.height(30.dp))
        ExpenseDescription(description, onDescriptionChange = { description = it })
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedCategory != null && description.isNotEmpty(),
            onClick = {
                val newExpense = Expense(
                    amount = price,
                    category = ExpenseCategory.valueOf(selectedCategory?.name ?: "other"),
                    description = description,
                )
                val expenseFromEdit = expense?.id?.let { newExpense.copy(id = it) }
                onSaveClick(expenseFromEdit ?: newExpense)
            }) {
            Text(text = "Save expense")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            CategoryBottomSheetContent(categories = categoryList, onSelectCategory = { category ->
                selectedCategory = category
                scopeCoroutine.launch { sheetState.hide() }.invokeOnCompletion {
                    showBottomSheet = false
                }
            })
        }
    }
}

@Composable
private fun ExpenseDescription(description: String, onDescriptionChange: (String) -> Unit) {
    Column {
        Text(text = "Description", style = MaterialTheme.typography.labelMedium)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description, onValueChange = onDescriptionChange,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
            ),
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun ExpenseTypeSelector(selectedCategory: ExpenseCategory?, openBottomSheet: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(
                "Expenses made for",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = selectedCategory?.name ?: "Select a category"
            )
        }
        IconButton(
            modifier = Modifier.clip(RoundedCornerShape(35))
                .background(MaterialTheme.colorScheme.surfaceContainer),
            onClick = openBottomSheet
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseAmount(
    priceContent: Double,
    onPriceChange: (Double) -> Unit,
) {
    var text by remember { mutableStateOf("$priceContent") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Amount", style = MaterialTheme.typography.labelMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = MaterialTheme.typography.titleMedium)
            TextField(
                modifier = Modifier.weight(1f),
                value = text,
                onValueChange = { newValue ->
                    val numericText = newValue.filter { it.isDigit() || it == '.' }
                    text = if (numericText.isNotEmpty() && numericText.count { it == '.' } <= 1) {
                        try {
                            val numericValue = numericText.toDouble()
                            onPriceChange(numericValue)
                            newValue
                        } catch (e: NumberFormatException) {
                            ""
                        }
                    } else {
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Decimal,
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.titleLarge,
            )

            Text(text = "USD", style = MaterialTheme.typography.labelLarge)
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
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