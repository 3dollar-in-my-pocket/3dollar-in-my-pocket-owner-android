package app.threedollars.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import app.threedollars.manager.feature.setting.navigation.navigateSetting
import app.threedollars.common.TabRoute
import app.threedollars.manager.navigation.factory.TabType

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = app.threedollars.common.TabRoute.Home::class

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateSetting() {
        navController.navigateSetting(navOptions {})
    }

    val currentTab: TabType?
        @Composable get() = TabType.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
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
