package app.threedollars.manager.feature.storemanagement.model

internal data class BusinessScheduleModel(
    val dayOfTWeek: String,
    val dayOfTheWeek: String,
    val locationDescription: String,
    val openingHours: String,
    val isOpen: Boolean,
    val isWeekend: Boolean,
)
