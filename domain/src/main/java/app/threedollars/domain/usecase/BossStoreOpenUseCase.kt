package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BossStoreOpenUseCase @Inject constructor(private val storeRepository: StoreRepository) {
    fun deleteBossStoreOpen(bossStoreId: String): Flow<Resource<String>> = storeRepository.deleteBossStoreOpen(bossStoreId)

    fun postBossStoreOpen(bossStoreId: String, mapLatitude: Double, mapLongitude: Double): Flow<Resource<String>> =
        storeRepository.postBossStoreOpen(bossStoreId, mapLatitude, mapLongitude)
}