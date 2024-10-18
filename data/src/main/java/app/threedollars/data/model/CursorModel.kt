package app.threedollars.data.model


import app.threedollars.domain.dto.CursorDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CursorModel(
    @SerialName("hasMore")
    val hasMore: Boolean = false,
    @SerialName("nextCursor")
    val nextCursor: String? = null,
) {
    fun toDto() = CursorDto(hasMore, nextCursor)
}