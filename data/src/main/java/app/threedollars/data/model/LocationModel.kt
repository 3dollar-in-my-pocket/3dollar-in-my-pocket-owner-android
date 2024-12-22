package app.threedollars.data.model


import app.threedollars.domain.dto.LocationDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    @SerialName("latitude")
    val latitude: Double? = null,
    @SerialName("longitude")
    val longitude: Double? = null,
) {
    fun toDto() = LocationDto(latitude, longitude)

}