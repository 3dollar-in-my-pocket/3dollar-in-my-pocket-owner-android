package app.threedollars.data.model


import app.threedollars.domain.dto.FavoriteDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteModel(
    @SerialName("isFavorite")
    val isFavorite: Boolean? = null,
) {
    fun toDto() = FavoriteDto(isFavorite)

}