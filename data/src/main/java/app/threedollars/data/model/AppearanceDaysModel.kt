package app.threedollars.data.model


import app.threedollars.domain.dto.AppearanceDaysDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppearanceDaysModel(
    @SerialName("dayOfTheWeek")
    val dayOfTheWeek: String? = null,
    @SerialName("openingHours")
    val openingHours: OpeningHoursModel? = null,
    @SerialName("locationDescription")
    val locationDescription: String? = null,
)

fun List<AppearanceDaysModel>.toDto() = map {
    AppearanceDaysDto(it.dayOfTheWeek, it.openingHours?.toDto(), it.locationDescription)
}
