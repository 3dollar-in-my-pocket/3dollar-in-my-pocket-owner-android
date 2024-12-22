package app.threedollars.data.model


import app.threedollars.domain.dto.AppearanceDaysRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppearanceDaysRequestModel(
    @SerialName("dayOfTheWeek")
    val dayOfTheWeek: String? = null,
    @SerialName("startTime")
    val startTime: String? = null,
    @SerialName("endTime")
    val endTime: String? = null,
    @SerialName("locationDescription")
    val locationDescription: String? = null,
) {
    fun toDto() = AppearanceDaysRequestDto(dayOfTheWeek, startTime, endTime, locationDescription)
}