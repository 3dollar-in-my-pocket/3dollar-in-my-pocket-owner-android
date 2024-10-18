package app.threedollars.domain.usecase

import app.threedollars.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppConfigUseCase @Inject constructor(private val appConfigRepository: AppConfigRepository) {
    suspend fun saveVersionName(version: String): Flow<Unit> = appConfigRepository.saveVersionName(version)

    suspend fun saveApplicationId(applicationId: String): Flow<Unit> = appConfigRepository.saveApplicationId(applicationId)
}