import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.AppTheme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Destination
import navigation.Navigation
import navigation.TitleTopBarTitles
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(isDark: Boolean = false) {
    PreComposeApp {
        AppTheme(isDark = isDark) {
            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopBar(navigator)
            val isEditOrAdd = titleTopBar != TitleTopBarTitles.DASHBOARD.value

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = titleTopBar) },
                        navigationIcon = {
                            if (isEditOrAdd) {
                                IconButton(
                                    onClick = { navigator.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Back button"
                                    )
                                }
                            } else {
                                Icon(
                                    modifier = Modifier.padding(start = 8.dp),
                                    imageVector = Icons.Default.Apps,
                                    contentDescription = "Back button"
                                )
                            }
                        }
                    )
                },
                floatingActionButton = {
                    if (!isEditOrAdd) {
                        FloatingActionButton(
                            onClick = { navigator.navigate(Destination.EditCreate.route) },
                            shape = RoundedCornerShape(50),
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add new expense"
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Navigation(modifier = Modifier.padding(paddingValues), navigator = navigator)
            }
        }
    }
}

@Composable
fun getTitleTopBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBarTitles.DASHBOARD
    val isOnCreate =
        navigator.currentEntry.collectAsState(null).value?.route?.route.equals(Destination.EditCreate.route + "/{id}?")
    if (isOnCreate) {
        titleTopBar = TitleTopBarTitles.CREATE
    }

    val isOnEdit = navigator.currentEntry.collectAsState(null).value?.path<Long>("id")
    isOnEdit?.let {
        titleTopBar = TitleTopBarTitles.EDIT
    }

    return titleTopBar.value
}
