package app.threedollars.data.model

import app.threedollars.domain.dto.AddressDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("fullAddress")
    val fullAddress: String? = "",
) {
    fun toDto() = AddressDto(fullAddress ?: "")
}
