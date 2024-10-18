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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.threedollars.manager.feature.setting.navigation.settingNavGraph
import app.threedollars.manager.main.ui.HomeScreen
import app.threedollars.manager.screen.BottomNavigation
import app.threedollars.manager.storeManagement.ui.StoreManagementScreen
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
    val navController = rememberNavController()
    val navigator: MainNavigator = rememberMainNavigator()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
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
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen()
        }
        composable(BottomNavItem.StoreManagement.screenRoute) {
            StoreManagementScreen()
        }
        settingNavGraph(
            onMoveLoginPage = {
                context.startActivity(Intent(context, LoginActivity::class.java))
                context.findActivity().finish()
            },
        )
    }
}
