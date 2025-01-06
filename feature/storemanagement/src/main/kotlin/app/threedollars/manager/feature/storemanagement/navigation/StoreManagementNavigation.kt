package app.threedollars.manager.feature.storemanagement.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import app.threedollars.common.TabRoute
import app.threedollars.manager.feature.storemanagement.StoreManagementRoute

fun NavController.navigateStoreManagement(navOptions: NavOptions) {
    navigate(
        route = TabRoute.StoreManagement,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.storeManagementNavGraph(
) {
    composable<TabRoute.StoreManagement> {
        StoreManagementRoute()
    }
}
