package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.BossEnumsDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EnumMapperUseCase @Inject constructor(private val storeRepository: StoreRepository){
    fun getBossEnums(): Flow<Resource<BossEnumsDto>> = storeRepository.getBossEnums()
}