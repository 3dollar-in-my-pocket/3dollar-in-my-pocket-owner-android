package app.threedollars.data.model


import app.threedollars.domain.dto.CategoryInfoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryInfoModel(
    @SerialName("category")
    val category: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("displayOrder")
    val displayOrder: String = "",
) {
    fun toDto() = CategoryInfoDto(category, description, displayOrder)

}