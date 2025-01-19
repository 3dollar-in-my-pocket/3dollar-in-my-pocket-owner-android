package app.threedollars.manager.feature.storemanagement.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CategoriesVo(
    val category: String,
    val categoryId: String,
    val description: String,
    val imageUrl: String,
    val isNew: Boolean,
    val name: String
)