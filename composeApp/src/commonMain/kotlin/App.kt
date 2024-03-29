import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.AppTheme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(isDark: Boolean = false) {
    PreComposeApp {
        AppTheme(isDark = isDark) {
            val navigator = rememberNavigator()
            val titleTopBar = "Expenses"

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = titleTopBar) },
                    )
                }
            ) { paddingValues ->
                Navigation(modifier = Modifier.padding(paddingValues), navigator = navigator)
            }
        }
    }
}
