package app.threedollars.data.model


import app.threedollars.domain.dto.OpeningHoursDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpeningHoursModel(
    @SerialName("startTime")
    val startTime: String? = null,
    @SerialName("endTime")
    val endTime: String? = null,
) {
    fun toDto() = OpeningHoursDto(startTime, endTime)
}