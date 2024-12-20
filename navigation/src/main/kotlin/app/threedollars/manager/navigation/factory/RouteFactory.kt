package app.threedollars.manager.navigation.factory

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import app.threedollars.manager.navigation.R

enum class TabType(
    @DrawableRes val icon: Int,
    val route: app.threedollars.common.TabRoute,
) {
    HOME(
        icon = R.drawable.ic_home,
        route = app.threedollars.common.TabRoute.Home
    ),
    STORE_MANAGEMENT(
        icon = R.drawable.ic_truck,
        route = app.threedollars.common.TabRoute.StoreManagement
    ),
    SETTING(
        icon = R.drawable.ic_my,
        route = app.threedollars.common.TabRoute.Setting
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (app.threedollars.common.TabRoute) -> Boolean): TabType? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (app.threedollars.common.Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}