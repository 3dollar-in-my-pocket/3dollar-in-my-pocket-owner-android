package app.threedollars.manager.feature.storemanagement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.dto.MenusDto
import app.threedollars.domain.usecase.BossStoreRetrieveUseCase
import app.threedollars.domain.usecase.BossStoreUseCase
import app.threedollars.domain.usecase.EnumMapperUseCase
import app.threedollars.domain.usecase.FeedbackUseCase
import app.threedollars.domain.usecase.ImageUploadUseCase
import app.threedollars.domain.usecase.PlatformStoreCategoryUseCase
import app.threedollars.manager.feature.storemanagement.components.ScheduleDay
import app.threedollars.manager.feature.storemanagement.model.AppearanceDaysVo
import app.threedollars.manager.feature.storemanagement.model.BossStorePatchModel
import app.threedollars.manager.feature.storemanagement.model.OpeningHoursVo
import app.threedollars.manager.feature.storemanagement.model.dtoToVo
import app.threedollars.manager.feature.storemanagement.model.toDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
internal class StoreManagementViewModel @Inject constructor(
    private val bossStoreRetrieveUseCase: BossStoreRetrieveUseCase,
    private val bossStoreUseCase: BossStoreUseCase,
    private val imageUploadUseCase: ImageUploadUseCase,
    private val platformStoreCategoryUseCase: PlatformStoreCategoryUseCase,
    private val enumMapperUseCase: EnumMapperUseCase,
    private val feedbackUseCase: FeedbackUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<StoreManagementState> = MutableStateFlow(StoreManagementState())

    val stateFlow: StateFlow<StoreManagementState> = _stateFlow.asStateFlow()

    val feedbackSpecific by lazy {
        feedbackUseCase.getFeedbackSpecific(
            targetId = _stateFlow.value.bossStoreRetrieve.bossStoreId
        ).cachedIn(viewModelScope)
    }

    fun updateScreenType(screenType: ScreenType) {
        _stateFlow.update {
            it.copy(
                screenType = screenType
            )
        }
    }

    fun updateDialogType(dialogType: DialogType) {
        _stateFlow.update {
            it.copy(
                dialogType = dialogType
            )
        }
    }

    fun getBossStoreRetrieveMe() {
        viewModelScope.launch {
            bossStoreRetrieveUseCase.getBossStoreRetrieveMe().collect {
                if (it.code.toString() == "200") {
                    it.data?.let { data ->
                        val scheduleDays = defaultScheduleDays.toMutableList()
                        data.appearanceDays.forEach { appearance ->
                            val index = scheduleDays.indexOfFirst { scheduleDay ->
                                scheduleDay.dayOfTheWeek == appearance.dayOfTheWeek
                            }
                            scheduleDays[index] = scheduleDays[index].copy(
                                startTime = appearance.openingHours?.startTime.toStringDefault(),
                                endTime = appearance.openingHours?.endTime.toStringDefault(),
                                locationDescription = appearance.locationDescription.toStringDefault(),
                                isSelected = true,
                            )
                        }

                        val appearanceDays = _stateFlow.value.appearanceDays
                        scheduleDays.forEach { scheduleDay ->
                            if (scheduleDay.startTime.isNotEmpty() && scheduleDay.endTime.isNotEmpty()) {
                                appearanceDays[scheduleDay.dayOfTheWeek] = AppearanceDaysVo(
                                    dayOfTheWeek = scheduleDay.dayOfTheWeek,
                                    openingHours = OpeningHoursVo(
                                        startTime = scheduleDay.startTime,
                                        endTime = scheduleDay.endTime
                                    ),
                                    locationDescription = scheduleDay.locationDescription,
                                )
                            }
                        }

                        _stateFlow.update { state ->
                            state.copy(
                                bossStoreRetrieve = data.dtoToVo(),
                                selectedStoreCategories = data.categories.map { dto -> dto.categoryId.toStringDefault() },
                                scheduleDays = scheduleDays,
                                appearanceDays = appearanceDays
                            )
                        }
                    }
                }
            }
        }
    }

    fun patchBossStore(
        bossStorePatchModel: BossStorePatchModel,
    ) {
        viewModelScope.launch {
            if (bossStorePatchModel.imageRequestBody == null) {
                bossStoreUseCase.patchBossStore(
                    bossStoreId = bossStorePatchModel.bossStoreId,
                    appearanceDays = bossStorePatchModel.appearanceDays,
                    categoriesIds = bossStorePatchModel.categoriesIds,
                    imageUrl = bossStorePatchModel.imageUrl,
                    introduction = bossStorePatchModel.introduction,
                    menus = bossStorePatchModel.menus?.map { it.toDto() },
                    name = bossStorePatchModel.name,
                    snsUrl = bossStorePatchModel.snsUrl,
                    accountNumber = bossStorePatchModel.accountNumber,
                    accountHolder = bossStorePatchModel.accountHolder,
                    accountBank = bossStorePatchModel.accountBank
                ).collect {
                    if (it.code == "200") {
                        _stateFlow.update { state ->
                            state.copy(
                                screenType = ScreenType.STORE_INFO
                            )
                        }
                    }
                    if (!it.errorMessage.isNullOrEmpty()) {
                        _stateFlow.update { state ->
                            state.copy(
                                dialogType = DialogType.ERROR_DIALOG,
                                errorMessage = it.errorMessage.toString()
                            )
                        }
                    }
                }
            } else {
                imageUploadUseCase.postImageUpload(
                    fileType = "BOSS_STORE_CERTIFICATION_IMAGE",
                    requestBody = bossStorePatchModel.imageRequestBody
                ).collect { it ->
                    if (it.code == "200") {
                        bossStoreUseCase.patchBossStore(
                            bossStoreId = bossStorePatchModel.bossStoreId,
                            appearanceDays = bossStorePatchModel.appearanceDays,
                            categoriesIds = bossStorePatchModel.categoriesIds,
                            imageUrl = it.data?.imageUrl.toStringDefault(),
                            introduction = bossStorePatchModel.introduction,
                            menus = bossStorePatchModel.menus?.map { it.toDto() },
                            name = bossStorePatchModel.name,
                            snsUrl = bossStorePatchModel.snsUrl,
                            accountNumber = bossStorePatchModel.accountNumber,
                            accountHolder = bossStorePatchModel.accountHolder,
                            accountBank = bossStorePatchModel.accountBank
                        ).collect {
                            if (it.code == "200") {
                                _stateFlow.update { state ->
                                    state.copy(
                                        screenType = ScreenType.STORE_INFO
                                    )
                                }
                            }
                        }
                    }
                    if (!it.errorMessage.isNullOrEmpty()) {
                        _stateFlow.update { state ->
                            state.copy(
                                dialogType = DialogType.ERROR_DIALOG,
                                errorMessage = it.errorMessage.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

    fun getBankEnum() {
        viewModelScope.launch {
            enumMapperUseCase.getBossEnums().collect { enums ->
                enums.data?.let { data ->
                    _stateFlow.update {
                        it.copy(
                            bankTypes = data.dtoToVo()
                        )
                    }
                }
            }
        }
    }

    fun getCategory() {
        viewModelScope.launch {
            val selectedStoreCategories = _stateFlow.value.selectedStoreCategories

            platformStoreCategoryUseCase.getStoreCategories("BOSS_STORE").collect {
                if (it.code.toString() == "200") {
                    it.data?.let { categoriesDtoList ->
                        val categoriesVo = categoriesDtoList.map { storeCategoriesDto ->
                            storeCategoriesDto.dtoToVo().copy(
                                isSelected = selectedStoreCategories.indexOf(storeCategoriesDto.categoryId) > -1
                            )
                        }
                        _stateFlow.update { state ->
                            state.copy(
                                storeCategories = categoriesVo
                            )
                        }
                    }
                }
                if (!it.errorMessage.isNullOrEmpty()) {
                    _stateFlow.update { state ->
                        state.copy(
                            dialogType = DialogType.ERROR_DIALOG,
                            errorMessage = it.errorMessage.toString(),
                        )
                    }
                }
            }
        }
    }

    fun categorySelection(index: Int) {
        val selectedStoreCategories = _stateFlow.value.selectedStoreCategories
        val storeCategories = _stateFlow.value.storeCategories.mapIndexed { mapIndex, storeCategory ->
            when {
                mapIndex != index -> storeCategory // 인덱스가 다르면 그대로 반환
                storeCategory.isSelected -> storeCategory.copy(isSelected = false) // 이미 선택된 경우 선택 해제
                selectedStoreCategories.size < 3 -> storeCategory.copy(isSelected = true) // 선택 제한 이내면 선택
                else -> storeCategory // 선택 제한 초과 시 그대로 반환
            }
        }

        _stateFlow.update { state ->
            state.copy(
                storeCategories = storeCategories,
                selectedStoreCategories = storeCategories.filter {
                    it.isSelected
                }.map {
                    it.categoryId.toStringDefault()
                }
            )
        }
    }

    fun patchMenu(
        bossStorePatchModel: BossStorePatchModel,
    ) {
        val list = bossStorePatchModel.menus?.filter { it.imageRequestBody != null }?.map {
            it.imageRequestBody as RequestBody
        }
        _stateFlow.update {
            it.copy(
                dialogType = DialogType.LOADING_DIALOG,
                errorMessage = null
            )
        }
        viewModelScope.launch {
            if (bossStorePatchModel.menus.isNullOrEmpty()) {
                bossStoreUseCase.patchBossStore(
                    bossStoreId = bossStorePatchModel.bossStoreId,
                    menus = listOf()
                ).collect {
                    _stateFlow.update {
                        it.copy(
                            screenType = ScreenType.STORE_INFO,
                            dialogType = DialogType.NONE
                        )
                    }
                }
            } else {
                if (list.isNullOrEmpty()) {
                    val menus = bossStorePatchModel.menus.filter {
                        !it.name.isNullOrEmpty() &&
                                it.price != null &&
                                it.price!! > 0
                    }.map {
                        MenusDto(
                            name = it.name,
                            price = it.price,
                        )
                    }
                    bossStoreUseCase.patchBossStore(
                        bossStoreId = bossStorePatchModel.bossStoreId,
                        menus = menus
                    ).collect {
                        _stateFlow.update {
                            it.copy(
                                screenType = ScreenType.STORE_INFO,
                                dialogType = DialogType.NONE
                            )
                        }
                    }
                } else {
                    Log.e("3q28409328490", list.toString())
                    imageUploadUseCase.postImageUploadBulk(
                        fileType = "BOSS_STORE_MENU_IMAGE",
                        requestBodyList = list
                    ).collect { resource ->
                        if (resource.code == "200") {
                            resource.data?.let { dtoList ->
                                dtoList.mapIndexed { index, imageUploadDto ->
                                    bossStorePatchModel.menus[index].copy(
                                        imageUrl = imageUploadDto.imageUrl
                                    )
                                }.run {
                                    Log.e("%56555542523w4", this.toString())
                                    val menus = bossStorePatchModel.menus.map { menuModel ->
                                        MenusDto(
                                            imageUrl = menuModel.imageUrl,
                                            name = menuModel.name,
                                            price = menuModel.price,
                                        )
                                    }
                                    bossStoreUseCase.patchBossStore(
                                        bossStoreId = bossStorePatchModel.bossStoreId,
                                        menus = menus
                                    ).collect {
                                        _stateFlow.update { state ->
                                            if (it.data == "OK") {
                                                state.copy(
                                                    screenType = ScreenType.STORE_INFO,
                                                    dialogType = DialogType.NONE
                                                )
                                            } else {
                                                state.copy(
                                                    errorMessage = it.errorMessage,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateDaysStartTime(day: String, time: String) {
        _stateFlow.update { state ->
            val updatedAppearanceDays = HashMap(state.appearanceDays)
            val appearanceDaysVo = updatedAppearanceDays[day]

            if (appearanceDaysVo != null) {
                val openingHours = appearanceDaysVo.openingHours.copy(startTime = time)
                updatedAppearanceDays[day] = appearanceDaysVo.copy(openingHours = openingHours)
            }

            state.copy(appearanceDays = updatedAppearanceDays)
        }
    }

    fun updateDaysEndTime(day: String, time: String) {
        _stateFlow.update { state ->
            val updatedAppearanceDays = HashMap(state.appearanceDays)
            val appearanceDaysVo = updatedAppearanceDays[day]

            if (appearanceDaysVo != null) {
                val openingHours = appearanceDaysVo.openingHours.copy(endTime = time)
                updatedAppearanceDays[day] = appearanceDaysVo.copy(openingHours = openingHours)
            }

            state.copy(appearanceDays = updatedAppearanceDays)
        }
    }

    fun updateDaysLocationDescription(day: String, locationDescription: String) {
        _stateFlow.update { state ->
            val updatedAppearanceDays = HashMap(state.appearanceDays)
            val appearanceDaysVo = updatedAppearanceDays[day]

            if (appearanceDaysVo != null) {
                updatedAppearanceDays[day] = appearanceDaysVo.copy(locationDescription = locationDescription)
            }

            state.copy(
                appearanceDays = updatedAppearanceDays
            )
        }
    }

    fun updateScheduleDay(scheduleDay: ScheduleDay) {
        val scheduleDays = _stateFlow.value.scheduleDays.toMutableList()
        val appearanceDays = _stateFlow.value.appearanceDays
        val index = scheduleDays.indexOfFirst { it.dayOfTheWeek == scheduleDay.dayOfTheWeek }
        scheduleDays[index] = scheduleDay
        scheduleDays.forEach {
            if (it.isSelected) {
                appearanceDays[it.dayOfTheWeek] = AppearanceDaysVo(
                    it.dayOfTheWeek,
                    OpeningHoursVo(it.startTime, it.endTime),
                    it.locationDescription,
                )
            } else {
                appearanceDays.remove(it.dayOfTheWeek)
            }
        }
        _stateFlow.update { state ->
            state.copy(
                scheduleDays = scheduleDays,
                appearanceDays = appearanceDays
            )
        }
    }

    fun getFeedbackFull() {
        viewModelScope.launch {
            feedbackUseCase.getFeedbackFull(
                targetType = "BOSS_STORE",
                targetId = _stateFlow.value.bossStoreRetrieve.bossStoreId
            ).collect { feedbackFullDto ->
                val feedbackFulls = feedbackFullDto.data?.map { dto -> dto.dtoToVo() }
                if (feedbackFulls != null) {
                    _stateFlow.update { state ->
                        state.copy(
                            feedbackFulls = feedbackFulls
                        )
                    }
                }
            }
        }
    }

    fun getFeedbackType() {
        viewModelScope.launch {
            feedbackUseCase.getFeedbackTypes(
                targetType = "BOSS_STORE"
            ).collect {
                val feedbackTypes = it.data?.map { dto -> dto.dtoToVo() }

                if (feedbackTypes != null) {
                    _stateFlow.update { state ->
                        state.copy(
                            feedbackTypes = feedbackTypes
                        )
                    }
                }
            }
        }
    }
}
