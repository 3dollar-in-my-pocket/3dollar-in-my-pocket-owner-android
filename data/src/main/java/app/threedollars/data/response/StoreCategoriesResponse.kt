package app.threedollars.data.response


import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.dto.StoreCategoriesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreCategoriesResponse(
    @SerialName("category")
    val category: String? = "",
    @SerialName("categoryId")
    val categoryId: String? = "",
    @SerialName("description")
    val description: String? = "",
    @SerialName("imageUrl")
    val imageUrl: String? = "",
    @SerialName("isNew")
    val isNew: Boolean? = false,
    @SerialName("name")
    val name: String? = "",
)

fun List<StoreCategoriesResponse>.toDto() = map {
    StoreCategoriesDto(
        category = it.category.toStringDefault(),
        categoryId = it.categoryId.toStringDefault(),
        description = it.description.toStringDefault(),
        imageUrl = it.imageUrl.toStringDefault(),
        isNew = it.isNew ?: false,
        name = it.name.toStringDefault()
    )
}
