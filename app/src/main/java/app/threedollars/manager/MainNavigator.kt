package app.threedollars.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import app.threedollars.manager.feature.setting.navigation.navigateSetting

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = BottomNavItem.Home.screenRoute

    fun navigateSetting() {
        navController.navigateSetting(navOptions {})
    }

    fun popBackStack() {
        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navController.popBackStack()
        }
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator {
    return remember(navController) {
        MainNavigator(navController)
    }
}
