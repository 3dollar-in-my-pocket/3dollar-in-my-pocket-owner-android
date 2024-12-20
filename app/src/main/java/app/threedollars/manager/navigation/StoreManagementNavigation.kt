package app.threedollars.manager.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import app.threedollars.common.TabRoute
import app.threedollars.manager.storeManagement.ui.StoreManagementScreen

fun NavController.navigateStoreManagement(navOptions: NavOptions) {
    navigate(
        route = TabRoute.StoreManagement,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.storeManagementNavGraph(
) {
    composable<TabRoute.StoreManagement> {
        StoreManagementScreen()
    }
}
