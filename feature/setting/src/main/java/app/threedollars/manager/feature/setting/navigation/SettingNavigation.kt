package app.threedollars.manager.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import app.threedollars.common.TabRoute
import app.threedollars.manager.feature.setting.SettingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSetting(navOptions: NavOptions) {
    navigate(
        route = TabRoute.Setting,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.settingNavGraph(
    onMoveLoginPage: () -> Unit,
) {
    composable<TabRoute.Setting> {
        SettingRoute(
            onMoveLoginPage = onMoveLoginPage,
        )
    }
}
