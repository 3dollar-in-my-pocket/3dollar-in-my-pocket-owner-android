package app.threedollars.manager.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import app.threedollars.common.TabRoute
import app.threedollars.manager.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(
        route = TabRoute.Home,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.homeNavGraph() {
    composable<TabRoute.Home> {
        HomeRoute()
    }
}