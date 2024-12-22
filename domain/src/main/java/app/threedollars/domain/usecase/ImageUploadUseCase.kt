package app.threedollars.domain.usecase

import app.threedollars.common.Resource
import app.threedollars.domain.dto.ImageUploadDto
import app.threedollars.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject

class ImageUploadUseCase @Inject constructor(private val storeRepository: StoreRepository){
    fun postImageUpload(fileType: String, requestBody: RequestBody): Flow<Resource<ImageUploadDto>> {
        return storeRepository.postImageUpload(fileType, requestBody)
    }

    fun postImageUploadBulk(fileType: String, requestBodyList: List<RequestBody>): Flow<Resource<List<ImageUploadDto>>> =
        storeRepository.postImageUploadBulk(fileType, requestBodyList)
}