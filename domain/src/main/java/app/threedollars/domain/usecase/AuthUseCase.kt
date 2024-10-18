package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.LoginDto
import app.threedollars.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun saveSocialAccessToken(token: String): Flow<Unit> = userRepository.saveSocialAccessToken(token)

    suspend fun saveAccessToken(token: String) = userRepository.saveAccessToken(token)
    fun getAccessToken(): Flow<Resource<String>> = userRepository.getAccessToken()

    fun getSocialAccessToken(): Flow<Resource<String>> = userRepository.getSocialAccessToken()

    fun login(socialType: String, token: String): Flow<Resource<LoginDto>> =
        userRepository.login(socialType, token)

    fun logout(): Flow<Resource<String>> =
        userRepository.logout()

    fun signUp(
        bossName: String,
        businessNumber: String,
        certificationPhotoUrl: String,
        socialType: String,
        storeCategoriesIds: List<String>,
        storeName: String,
        token: String,
    ): Flow<Resource<LoginDto>> =
        userRepository.signUp(bossName, businessNumber, certificationPhotoUrl, socialType, storeCategoriesIds, storeName, token)

    fun signOut(): Flow<Resource<String>> =
        userRepository.signOut()

}