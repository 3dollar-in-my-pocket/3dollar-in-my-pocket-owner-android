package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BossDeviceUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun putBossDevice(pushPlatformType: String, pushToken: String): Flow<Resource<String>> =
        userRepository.putBossDevice(pushPlatformType, pushToken)

    fun deleteBossDevice(): Flow<Resource<String>> = userRepository.deleteBossDevice()

    fun putBossDeviceToken(pushPlatformType: String, pushToken: String): Flow<Resource<String>> =
        userRepository.putBossDeviceToken(pushPlatformType, pushToken)
}