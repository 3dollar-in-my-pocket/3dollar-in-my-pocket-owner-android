package app.threedollars.manager.feature.storemanagement.model


data class StoreCategoriesVo(
    val category: String = "",
    val categoryId: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val isNew: Boolean = false,
    val name: String = "",
    var isSelected: Boolean = false
)