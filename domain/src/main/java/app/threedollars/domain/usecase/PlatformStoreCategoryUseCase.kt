package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.StoreCategoriesDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlatformStoreCategoryUseCase @Inject constructor(private val storeRepository: StoreRepository){
    fun getStoreCategories(storeType: String): Flow<Resource<List<StoreCategoriesDto>>> = storeRepository.getStoreCategories(storeType)
}