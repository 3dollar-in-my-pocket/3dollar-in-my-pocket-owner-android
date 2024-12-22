package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.FaqCategoriesDto
import app.threedollars.domain.dto.FaqDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FaqUseCase @Inject constructor(private val storeRepository: StoreRepository) {
    fun getFaqCategories(): Flow<Resource<List<FaqCategoriesDto>>> = storeRepository.getFaqCategories()

    fun getFaqs(category: String = ""): Flow<Resource<List<FaqDto>>> = storeRepository.getFaqs(category)
}