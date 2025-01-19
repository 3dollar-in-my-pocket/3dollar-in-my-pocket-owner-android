package app.threedollars.manager.feature.storemanagement.model

import app.threedollars.domain.dto.AppearanceDaysRequestDto
import okhttp3.RequestBody

internal data class BossStorePatchModel(
    val bossStoreId: String,
    val appearanceDays: List<AppearanceDaysRequestDto>? = null,
    val categoriesIds: List<String>? = null,
    val imageUrl: String? = null,
    val introduction: String? = null,
    val menus: List<MenuModel>? = null,
    val name: String? = null,
    val snsUrl: String? = null,
    val accountNumber: String? = null,
    val accountHolder: String? = null,
    val accountBank: String? = null,
    val imageRequestBody: RequestBody? = null,
)
