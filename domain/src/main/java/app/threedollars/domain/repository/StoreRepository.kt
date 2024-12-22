package app.threedollars.domain.repository

import androidx.paging.PagingData
import app.threedollars.common.Resource
import app.threedollars.domain.dto.*
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface StoreRepository {

    // boss-store-controller
    fun putBossStore(
        bossStoreId: String,
        appearanceDays: List<AppearanceDaysRequestDto> = listOf(),
        categoriesIds: List<String> = listOf(),
        imageUrl: String? = null,
        introduction: String? = null,
        menus: List<MenusDto> = listOf(),
        name: String? = null,
        snsUrl: String? = null
    ): Flow<Resource<String>>

    fun patchBossStore(
        bossStoreId: String,
        appearanceDays: List<AppearanceDaysRequestDto>? = null,
        categoriesIds: List<String>? = null,
        imageUrl: String? = null,
        introduction: String? = null,
        menus: List<MenusDto>? = null,
        name: String? = null,
        snsUrl: String? = null,
        accountNumber: String? = null,
        accountHolder: String? = null,
        accountBank: String? = null
    ): Flow<Resource<String>>

    // boss-store-open-controller
    fun deleteBossStoreOpen(bossStoreId: String): Flow<Resource<String>>

    fun postBossStoreOpen(bossStoreId: String, mapLatitude: Double, mapLongitude: Double): Flow<Resource<String>>

    // boss-store-retrieve-controller
    fun getBossStoreRetrieveSpecific(bossStoreId: String, latitude: Double, longitude: Double): Flow<Resource<BossStoreRetrieveDto>>

    fun getBossStoreRetrieveMe(): Flow<Resource<BossStoreRetrieveDto>>

    fun getBossStoreRetrieveAround(
        categoryId: String,
        distanceKm: Int,
        mapLatitude: Double,
        mapLongitude: Double,
        orderType: String,
        size: Int
    ): Flow<Resource<List<BossStoreRetrieveAroundDto>>>

    // enum-mapper-controller
    fun getBossEnums(): Flow<Resource<BossEnumsDto>>

    // faq-controller
    fun getFaqCategories(): Flow<Resource<List<FaqCategoriesDto>>>

    fun getFaqs(category: String = ""): Flow<Resource<List<FaqDto>>>

    // feedback-controller
    fun getFeedbackFull(targetType: String, targetId: String): Flow<Resource<List<FeedbackFullDto>>>

    fun getFeedbackSpecific(targetId: String): Flow<PagingData<ContentsDto>>

    fun getFeedbackTypes(targetType: String): Flow<Resource<List<FeedbackTypesDto>>>

    // image-upload-controller
    fun postImageUpload(fileType: String, requestBody: RequestBody): Flow<Resource<ImageUploadDto>>

    fun postImageUploadBulk(fileType: String, requestBodyList: List<RequestBody>): Flow<Resource<List<ImageUploadDto>>>

    // platform-store-category-controller
    fun getStoreCategories(storeType: String): Flow<Resource<List<StoreCategoriesDto>>>
}