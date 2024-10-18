package app.threedollars.data.model


import app.threedollars.domain.dto.OpenStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenStatusModel(
    @SerialName("openStartDateTime")
    val openStartDateTime: String? = null,
    @SerialName("status")
    val status: String? = null,
) {
    fun toDto() = OpenStatusDto(openStartDateTime, status)
}