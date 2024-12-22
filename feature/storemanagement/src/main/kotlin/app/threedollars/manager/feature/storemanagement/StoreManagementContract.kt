package app.threedollars.manager.feature.storemanagement


internal data class StoreManagementState(
    val screenType: ScreenType = ScreenType.STORE_INFO,
)

internal enum class ScreenType {
    STORE_INFO, REVIEW_INFO
}
