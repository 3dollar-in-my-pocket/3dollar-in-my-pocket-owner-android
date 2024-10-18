package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.AppearanceDaysRequestDto
import app.threedollars.domain.dto.MenusDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BossStoreUseCase @Inject constructor(private val storeRepository: StoreRepository) {

    fun putBossStore(
        bossStoreId: String,
        appearanceDays: List<AppearanceDaysRequestDto> = listOf(),
        categoriesIds: List<String> = listOf(),
        imageUrl: String? = null,
        introduction: String? = null,
        menus: List<MenusDto> = listOf(),
        name: String? = null,
        snsUrl: String? = null,
    ): Flow<Resource<String>> =
        storeRepository.putBossStore(bossStoreId, appearanceDays, categoriesIds, imageUrl, introduction, menus, name, snsUrl)

    fun patchBossStore(
        bossStoreId: String,
        appearanceDays: List<AppearanceDaysRequestDto>? = null,
        categoriesIds: List<String>? = null,
        imageUrl: String? = null,
        introduction: String? = null,
        menus: List<MenusDto>? = null,
        name: String? = null,
        snsUrl: String? = null,
        accountNumber: String? = null,
        accountHolder: String? = null,
        accountBank: String? = null,
    ): Flow<Resource<String>> =
        storeRepository.patchBossStore(
            bossStoreId = bossStoreId,
            appearanceDays = appearanceDays,
            categoriesIds = categoriesIds,
            imageUrl = imageUrl,
            introduction = introduction,
            menus = menus,
            name = name,
            snsUrl = snsUrl,
            accountNumber = accountNumber,
            accountHolder = accountHolder,
            accountBank = accountBank
        )
}