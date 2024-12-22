package app.threedollars.data.model

import app.threedollars.domain.dto.BankDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    @SerialName("key")
    val key: String? = "",
    @SerialName("description")
    val description: String? = "",
) {
    fun toDto() = BankDto(key = key ?: "", description = description ?: "")
}
