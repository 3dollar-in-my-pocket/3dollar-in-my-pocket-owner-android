package app.threedollars.domain.usecase

import androidx.paging.PagingData
import app.threedollars.common.Resource
import app.threedollars.domain.dto.ContentsDto
import app.threedollars.domain.dto.FeedbackFullDto
import app.threedollars.domain.dto.FeedbackTypesDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedbackUseCase @Inject constructor(private val storeRepository: StoreRepository) {
    fun getFeedbackFull(targetType: String, targetId: String): Flow<Resource<List<FeedbackFullDto>>> =
        storeRepository.getFeedbackFull(targetType, targetId)

    fun getFeedbackSpecific(targetId: String): Flow<PagingData<ContentsDto>> =
        storeRepository.getFeedbackSpecific(targetId)

    fun getFeedbackTypes(targetType: String): Flow<Resource<List<FeedbackTypesDto>>> =
        storeRepository.getFeedbackTypes(targetType)

}