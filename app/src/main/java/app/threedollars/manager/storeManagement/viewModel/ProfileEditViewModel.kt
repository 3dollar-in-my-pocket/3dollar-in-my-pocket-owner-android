package app.threedollars.manager.storeManagement.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.common.EventFlow
import app.threedollars.common.MutableEventFlow
import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.usecase.BossStoreRetrieveUseCase
import app.threedollars.domain.usecase.BossStoreUseCase
import app.threedollars.domain.usecase.ImageUploadUseCase
import app.threedollars.domain.usecase.PlatformStoreCategoryUseCase
import app.threedollars.manager.util.dtoToVo
import app.threedollars.manager.vo.BossStoreRetrieveVo
import app.threedollars.manager.vo.StoreCategoriesVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val platformStoreCategoryUseCase: PlatformStoreCategoryUseCase,
    private val bossStoreUseCase: BossStoreUseCase,
    private val bossStoreRetrieveUseCase: BossStoreRetrieveUseCase,
    private val imageUploadUseCase: ImageUploadUseCase
) : BaseViewModel() {

    init {
        getBossStoreRetrieveMe()
    }

    val categoryItemState = mutableStateListOf<StoreCategoriesVo>()
    private val _bossStoreRetrieveMe = MutableEventFlow<BossStoreRetrieveVo>()
    val bossStoreRetrieveMe: EventFlow<BossStoreRetrieveVo> get() = _bossStoreRetrieveMe
    private val _selectedItems = MutableStateFlow<List<String>>(emptyList())
    val selectedItems: StateFlow<List<String>> = _selectedItems
    private val _editComplete = MutableEventFlow<Boolean>()
    val editComplete: EventFlow<Boolean> get() = _editComplete
    private fun getCategory() {
        viewModelScope.launch(exceptionHandler) {
            platformStoreCategoryUseCase.getStoreCategories("BOSS_STORE").collect {
                if (it.code.toString() == "200") {
                    it.data?.let { categoriesDtoList ->
                        val categoriesVo = categoriesDtoList.map { storeCategoriesDto ->
                            storeCategoriesDto.dtoToVo().copy(isSelected = _selectedItems.value.indexOf(storeCategoriesDto.categoryId) > -1)
                        }
                        categoryItemState.addAll(categoriesVo)
                    }
                }
                if (!it.errorMessage.isNullOrEmpty()) {
                    setErrorMessage(it.errorMessage.toString())
                }
            }
        }
    }

    private fun getBossStoreRetrieveMe() {
        viewModelScope.launch(exceptionHandler) {
            bossStoreRetrieveUseCase.getBossStoreRetrieveMe().collect {
                if (it.code.toString() == "200") {
                    it.data?.let { data ->
                        _bossStoreRetrieveMe.emit(data.dtoToVo())
                        _selectedItems.value = data.categories.map { it.categoryId.toStringDefault() }
                        getCategory()
                    }
                }
                if (!it.errorMessage.isNullOrEmpty()) {
                    setErrorMessage(it.errorMessage.toString())
                }
            }
        }
    }

    fun patchBossStore(
        id: String,
        name: String,
        sns: String,
        imageRequestBody: RequestBody? = null
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (imageRequestBody == null) {
                bossStoreUseCase.patchBossStore(bossStoreId = id, name = name, snsUrl = sns, categoriesIds = selectedItems.value).collect {
                    if(it.code == "200") _editComplete.emit(true)
                    if (!it.errorMessage.isNullOrEmpty()) {
                        setErrorMessage(it.errorMessage.toString())
                    }
                }
            } else {
                imageUploadUseCase.postImageUpload("BOSS_STORE_CERTIFICATION_IMAGE", imageRequestBody).collect { it ->
                    if (it.code == "200") {
                        bossStoreUseCase.patchBossStore(
                            bossStoreId = id,
                            name = name,
                            snsUrl = sns,
                            categoriesIds = selectedItems.value,
                            imageUrl = it.data?.imageUrl.toStringDefault()
                        ).collect {
                            if(it.code == "200") _editComplete.emit(true)
                        }
                    }
                    if (!it.errorMessage.isNullOrEmpty()) {
                        setErrorMessage(it.errorMessage.toString())
                    }
                }
            }
        }
    }


    fun categorySelection(index: Int) {
        val item = categoryItemState[index]
        val isSelected = item.isSelected

        if (isSelected) {
            categoryItemState[index] = item.copy(isSelected = false)
        } else if (selectedItems.value.size < 3) {
            categoryItemState[index] = item.copy(isSelected = true)
        }
        _selectedItems.value = categoryItemState.filter { it.isSelected }.map { it.categoryId.toStringDefault() }
    }
}