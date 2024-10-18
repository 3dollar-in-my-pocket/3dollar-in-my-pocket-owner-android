package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.BossStoreRetrieveAroundDto
import app.threedollars.domain.dto.BossStoreRetrieveDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BossStoreRetrieveUseCase @Inject constructor(private val storeRepository: StoreRepository) {

    fun getBossStoreRetrieveSpecific(bossStoreId: String, latitude: Double, longitude: Double): Flow<Resource<BossStoreRetrieveDto>> =
        storeRepository.getBossStoreRetrieveSpecific(bossStoreId, latitude, longitude)

    fun getBossStoreRetrieveMe(): Flow<Resource<BossStoreRetrieveDto>> = storeRepository.getBossStoreRetrieveMe()

    fun getBossStoreRetrieveAround(
        categoryId: String = "",
        distanceKm: Int = 1,
        mapLatitude: Double,
        mapLongitude: Double,
        orderType: String = "DISTANCE_ASC",
        size: Int = 30,
    ): Flow<Resource<List<BossStoreRetrieveAroundDto>>> =
        storeRepository.getBossStoreRetrieveAround(categoryId, distanceKm, mapLatitude, mapLongitude, orderType, size)
}