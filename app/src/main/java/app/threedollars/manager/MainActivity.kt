package app.threedollars.manager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.NavHost
import app.threedollars.manager.ext.navigateTab
import app.threedollars.manager.feature.setting.navigation.settingNavGraph
import app.threedollars.manager.navigation.MainNavigator
import app.threedollars.manager.feature.home.navigation.homeNavGraph
import app.threedollars.manager.navigation.rememberMainNavigator
import app.threedollars.manager.navigation.storeManagementNavGraph
import app.threedollars.manager.screen.BottomNavigation
import app.threedollars.manager.util.findActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenView()
        }
    }

}

@Preview
@Composable
fun MainScreenView() {
    val navigator: MainNavigator = rememberMainNavigator()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                currentTab = navigator.currentTab,
                onTabSelected = {
                    navigator.navController.navigateTab(it)
                }
            )
        }
    ) {
        NavigationGraph(navigator = navigator, it.calculateBottomPadding())
    }
}

@Composable
fun NavigationGraph(navigator: MainNavigator, calculateBottomPadding: Dp) {
    val context = LocalContext.current

    NavHost(
        modifier = Modifier.padding(bottom = calculateBottomPadding),
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        homeNavGraph()

        storeManagementNavGraph()

        settingNavGraph(
            onMoveLoginPage = {
                context.startActivity(Intent(context, LoginActivity::class.java))
                context.findActivity().finish()
            },
        )
    }
}
