package app.threedollars.manager.feature.storemanagement.model

internal data class BossStoreRetrieveVo(
    val bossStoreId: String = "",
    val isOwner: Boolean = true,
    val name: String = "",
    val location: LocationVo = LocationVo(),
    val address: AddressVo = AddressVo(),
    val imageUrl: String = "",
    val introduction: String = "",
    val snsUrl: String = "",
    val menus: List<MenusVo> = listOf(),
    val appearanceDays: List<AppearanceDaysVo> = listOf(),
    val categories: List<CategoriesVo> = listOf(),
    val accountNumbers: List<AccountNumbersVo> = listOf(),
    val createdAt: String = "",
    val distance: Int = 0,
    val openStatus: OpenStatusVo = OpenStatusVo(),
    val updatedAt: String = "",
)
