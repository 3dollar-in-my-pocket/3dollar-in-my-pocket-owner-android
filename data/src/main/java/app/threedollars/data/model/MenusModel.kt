package app.threedollars.data.model


import app.threedollars.domain.dto.MenusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenusModel(
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("price")
    val price: Int? = null,
)

fun List<MenusModel>.toDto() = map {
    MenusDto(it.imageUrl, it.name, it.price)
}
