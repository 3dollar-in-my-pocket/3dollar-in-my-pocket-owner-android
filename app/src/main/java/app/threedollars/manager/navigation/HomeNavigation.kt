package app.threedollars.manager.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import app.threedollars.manager.main.ui.HomeScreen
import app.threedollars.common.TabRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(
        route = TabRoute.Home,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.homeNavGraph() {
    composable<TabRoute.Home> {
        HomeScreen()
    }
}