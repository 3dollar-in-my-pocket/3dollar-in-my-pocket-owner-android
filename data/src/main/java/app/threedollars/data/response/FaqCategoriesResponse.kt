package app.threedollars.data.response


import app.threedollars.domain.dto.FaqCategoriesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FaqCategoriesResponse(
    @SerialName("category")
    val category: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("displayOrder")
    val displayOrder: Int? = null,
)

fun List<FaqCategoriesResponse>.toDto() = map {
    FaqCategoriesDto(it.category, it.description, it.displayOrder)
}
