package app.threedollars.data.model


import app.threedollars.domain.dto.CategoriesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesModel(
    @SerialName("category")
    val category: String? = null,
    @SerialName("categoryId")
    val categoryId: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("isNew")
    val isNew: Boolean? = null,
    @SerialName("name")
    val name: String? = null,
)

fun List<CategoriesModel>.toDto() = map {
    CategoriesDto(it.category, it.categoryId, it.description, it.imageUrl, it.isNew, it.name)
}
