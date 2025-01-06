package app.threedollars.manager.feature.storemanagement.model


internal data class AppearanceDaysVo(
    val dayOfTheWeek: String,
    val openingHours: OpeningHoursVo,
    val locationDescription: String
)