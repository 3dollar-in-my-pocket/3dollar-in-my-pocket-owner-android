package app.threedollars.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.threedollars.common.Resource
import app.threedollars.common.ext.toStringDefault
import app.threedollars.data.model.AppearanceDaysRequestModel
import app.threedollars.data.model.MenusModel
import app.threedollars.data.model.toDto
import app.threedollars.data.request.AccountNumberRequest
import app.threedollars.data.request.BossStoreRequest
import app.threedollars.data.response.toDto
import app.threedollars.domain.dto.AppearanceDaysRequestDto
import app.threedollars.domain.dto.BossEnumsDto
import app.threedollars.domain.dto.BossStoreRetrieveAroundDto
import app.threedollars.domain.dto.BossStoreRetrieveDto
import app.threedollars.domain.dto.ContentsDto
import app.threedollars.domain.dto.FaqCategoriesDto
import app.threedollars.domain.dto.FaqDto
import app.threedollars.domain.dto.FeedbackFullDto
import app.threedollars.domain.dto.FeedbackTypesDto
import app.threedollars.domain.dto.ImageUploadDto
import app.threedollars.domain.dto.MenusDto
import app.threedollars.domain.dto.StoreCategoriesDto
import app.threedollars.domain.repository.StoreRepository
import app.threedollars.network.NetworkService
import app.threedollars.source.FeedbackSpecificDataSource
import app.threedollars.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val networkService: NetworkService,
) : StoreRepository {
    override fun putBossStore(
        bossStoreId: String,
        appearanceDays: List<AppearanceDaysRequestDto>,
        categoriesIds: List<String>,
        imageUrl: String?,
        introduction: String?,
        menus: List<MenusDto>,
        name: String?,
        snsUrl: String?,
    ): Flow<Resource<String>> {
        val appearanceDaysModel = appearanceDays.map {
            AppearanceDaysRequestModel(it.dayOfTheWeek, it.startTime, it.endTime, it.locationDescription)
        }
        val menusModel = menus.map {
            MenusModel(it.imageUrl, it.name, it.price)
        }
        val bossStoreRequest = BossStoreRequest(
            appearanceDaysModel,
            categoriesIds,
            imageUrl,
            introduction,
            menusModel,
            name,
            snsUrl
        )
        return remoteDataSource.putBossStore(bossStoreId, bossStoreRequest)
    }

    override fun patchBossStore(
        bossStoreId: String,
        appearanceDays: List<AppearanceDaysRequestDto>?,
        categoriesIds: List<String>?,
        imageUrl: String?,
        introduction: String?,
        menus: List<MenusDto>?,
        name: String?,
        snsUrl: String?,
        accountNumber: String?,
        accountHolder: String?,
        accountBank: String?,
    ): Flow<Resource<String>> {
        val appearanceDaysModel = appearanceDays?.map {
            AppearanceDaysRequestModel(it.dayOfTheWeek, it.startTime, it.endTime, it.locationDescription)
        }
        val menusModel = menus?.map {
            MenusModel(it.imageUrl, it.name, it.price)
        }

        val accountNumberRequest = if (accountNumber != null &&
            accountHolder != null &&
            accountBank != null
        ) {
            listOf(
                AccountNumberRequest(
                    accountNumber = accountNumber.toStringDefault(),
                    accountHolder = accountHolder.toStringDefault(),
                    bank = accountBank.toStringDefault()
                )
            )
        } else null
        val bossStoreRequest = BossStoreRequest(
            appearanceDaysModel,
            categoriesIds,
            imageUrl,
            introduction,
            menusModel,
            name,
            snsUrl,
            accountNumbers = accountNumberRequest
        )
        return remoteDataSource.patchBossStore(bossStoreId, bossStoreRequest)
    }

    override fun deleteBossStoreOpen(bossStoreId: String): Flow<Resource<String>> =
        remoteDataSource.deleteBossStoreOpen(bossStoreId)

    override fun postBossStoreOpen(bossStoreId: String, mapLatitude: Double, mapLongitude: Double): Flow<Resource<String>> =
        remoteDataSource.postBossStoreOpen(bossStoreId, mapLatitude, mapLongitude)

    override fun getBossStoreRetrieveSpecific(
        bossStoreId: String,
        latitude: Double,
        longitude: Double,
    ): Flow<Resource<BossStoreRetrieveDto>> {
        return remoteDataSource.getBossStoreRetrieveSpecific(bossStoreId, latitude, longitude).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }
    }

    override fun getBossStoreRetrieveMe(): Flow<Resource<BossStoreRetrieveDto>> =
        remoteDataSource.getBossStoreRetrieveMe().map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun getBossStoreRetrieveAround(
        categoryId: String,
        distanceKm: Int,
        mapLatitude: Double,
        mapLongitude: Double,
        orderType: String,
        size: Int,
    ): Flow<Resource<List<BossStoreRetrieveAroundDto>>> {
        return remoteDataSource.getBossStoreRetrieveAround(categoryId, distanceKm, mapLatitude, mapLongitude, orderType, size)
            .map {
                if (it.data != null) {
                    Resource.Success(data = it.data!!.toDto(), code = it.code)
                } else {
                    Resource.Error(errorMessage = it.errorMessage, code = it.code)
                }
            }
    }

    override fun getBossEnums(): Flow<Resource<BossEnumsDto>> =
        remoteDataSource.getBossEnums().map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun getFaqCategories(): Flow<Resource<List<FaqCategoriesDto>>> =
        remoteDataSource.getFaqCategories().map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun getFaqs(category: String): Flow<Resource<List<FaqDto>>> =
        remoteDataSource.getFaqs(category).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun getFeedbackFull(targetType: String, targetId: String): Flow<Resource<List<FeedbackFullDto>>> =
        remoteDataSource.getFeedbackFull(targetType, targetId).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun getFeedbackSpecific(targetId: String): Flow<PagingData<ContentsDto>> = Pager(PagingConfig(pageSize = 10)) {
        FeedbackSpecificDataSource(networkService, targetId)
    }.flow.map {
        it.map { contentsModel ->
            contentsModel.toDto()
        }
    }

    override fun getFeedbackTypes(targetType: String): Flow<Resource<List<FeedbackTypesDto>>> =
        remoteDataSource.getFeedbackTypes(targetType).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun postImageUpload(fileType: String, requestBody: RequestBody): Flow<Resource<ImageUploadDto>> {
        return remoteDataSource.postImageUpload(fileType, requestBody).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }
    }

    override fun postImageUploadBulk(fileType: String, requestBodyList: List<RequestBody>): Flow<Resource<List<ImageUploadDto>>> =
        remoteDataSource.postImageUploadBulk(fileType, requestBodyList).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }

    override fun getStoreCategories(storeType: String): Flow<Resource<List<StoreCategoriesDto>>> =
        remoteDataSource.getStoreCategories(storeType).map {
            if (it.data != null) {
                Resource.Success(data = it.data!!.toDto(), code = it.code)
            } else {
                Resource.Error(errorMessage = it.errorMessage, code = it.code)
            }
        }
}