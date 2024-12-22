package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.BossAccountInfoDto
import app.threedollars.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BossAccountUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun getBossAccount(): Flow<Resource<BossAccountInfoDto>> = userRepository.getBossAccount()

    fun putBossAccount(name: String): Flow<Resource<String>> = userRepository.putBossAccount(name)
}