package app.threedollars.common

import kotlinx.serialization.Serializable

sealed interface TabRoute : Route {
    @Serializable
    data object Home : TabRoute

    @Serializable
    data object StoreManagement : TabRoute

    @Serializable
    data object Setting : TabRoute
}

sealed interface Route {

}