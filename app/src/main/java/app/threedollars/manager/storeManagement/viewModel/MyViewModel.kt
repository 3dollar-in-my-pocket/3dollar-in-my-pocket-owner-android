package app.threedollars.manager.storeManagement.viewModel

import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.common.EventFlow
import app.threedollars.common.MutableEventFlow
import app.threedollars.domain.dto.MenusDto
import app.threedollars.domain.usecase.BossStoreRetrieveUseCase
import app.threedollars.domain.usecase.BossStoreUseCase
import app.threedollars.domain.usecase.ImageUploadUseCase
import app.threedollars.manager.storeManagement.data.MenuModel
import app.threedollars.manager.util.dtoToVo
import app.threedollars.manager.vo.BossStoreRetrieveVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val bossStoreRetrieveUseCase: BossStoreRetrieveUseCase,
    private val bossStoreUseCase: BossStoreUseCase,
    private val imageUploadUseCase: ImageUploadUseCase,
) : BaseViewModel() {

    private val _bossStoreRetrieveMe = MutableEventFlow<BossStoreRetrieveVo>()
    val bossStoreRetrieveMe: EventFlow<BossStoreRetrieveVo> get() = _bossStoreRetrieveMe

    private val _isSuccess = MutableEventFlow<Boolean>()
    val isSuccess: EventFlow<Boolean> get() = _isSuccess
    fun getBossStoreRetrieveMe() {
        viewModelScope.launch {
            bossStoreRetrieveUseCase.getBossStoreRetrieveMe().collect {
                if (it.code.toString() == "200") {
                    it.data?.let { data ->
                        _bossStoreRetrieveMe.emit(data.dtoToVo())
                    }
                }
            }
        }
    }

    fun patchIntroduction(bossStoreId: String?, introduction: String) {
        viewModelScope.launch {
            if (bossStoreId != null) {
                bossStoreUseCase.patchBossStore(bossStoreId, introduction = introduction).collect {
                    _isSuccess.emit(it.data == "OK")
                }
            }
        }
    }

    fun patchMenu(bossStoreId: String?, fileType: String, menuModelList: List<MenuModel>) {
        val list = menuModelList.filter { it.imageRequestBody != null }.map {
            it.imageRequestBody as RequestBody
        }
        viewModelScope.launch {
            if (menuModelList.isEmpty()) {
                bossStoreUseCase.patchBossStore(bossStoreId.toString(), menus = listOf()).collect {
                    _isSuccess.emit(it.data == "OK")
                }
            } else {
                if (list.isEmpty()) {
                    val menus = menuModelList.filter { !it.name.isNullOrEmpty() && it.price != null && it.price!! > 0 }.map {
                        MenusDto(
                            name = it.name,
                            price = it.price,
                        )
                    }
                    bossStoreUseCase.patchBossStore(bossStoreId.toString(), menus = menus).collect {
                        _isSuccess.emit(it.data == "OK")
                    }
                } else {
                    imageUploadUseCase.postImageUploadBulk(fileType, list).collect { resource ->
                        if (resource.code == "200") {
                            resource.data?.let { dtoList ->
                                dtoList.mapIndexed { index, imageUploadDto ->
                                    menuModelList[index].imageUrl = imageUploadDto.imageUrl
                                }.run {
                                    val menus = menuModelList.map { menuModel ->
                                        MenusDto(
                                            imageUrl = menuModel.imageUrl,
                                            name = menuModel.name,
                                            price = menuModel.price,
                                        )
                                    }
                                    bossStoreUseCase.patchBossStore(bossStoreId.toString(), menus = menus).collect {
                                        _isSuccess.emit(it.data == "OK")
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
