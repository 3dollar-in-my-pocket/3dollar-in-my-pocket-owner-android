package app.threedollars.data.model


import app.threedollars.domain.dto.EnumsDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnumsModel(
    @SerialName("key")
    val key: String? = null,
    @SerialName("description")
    val description: String? = null,
)

fun List<EnumsModel>.toDto() = map {
    EnumsDto(it.key, it.description)
}