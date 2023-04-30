package app.threedollars.manager.sign.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.common.EventFlow
import app.threedollars.common.MutableEventFlow
import app.threedollars.domain.usecase.AuthUseCase
import app.threedollars.domain.usecase.ImageUploadUseCase
import app.threedollars.domain.usecase.PlatformStoreCategoryUseCase
import app.threedollars.manager.sign.LoginNavItem
import app.threedollars.manager.util.dtoToVo
import app.threedollars.manager.vo.StoreCategoriesVo
import com.kakao.sdk.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class SignViewModel @Inject constructor(
    private val platformStoreCategoryUseCase: PlatformStoreCategoryUseCase,
    private val authUseCase: AuthUseCase,
    private val imageUploadUseCase: ImageUploadUseCase
) : BaseViewModel() {

    val categoryItemState = mutableStateListOf<StoreCategoriesVo>()

    private val selectedItems = mutableListOf<StoreCategoriesVo>()

    private val _loginNavItem = MutableEventFlow<LoginNavItem>()
    val loginNavItem: EventFlow<LoginNavItem> get() = _loginNavItem

    init {
        getCategory()
    }

    private fun getCategory() {
        viewModelScope.launch(exceptionHandler) {
            platformStoreCategoryUseCase.getStoreCategories("BOSS_STORE").collect {
                if (it.code.toString() == "200") {
                    it.data?.let { categoriesDtoList ->
                        val categoriesVo = categoriesDtoList.map { storeCategoriesDto ->
                            storeCategoriesDto.dtoToVo()
                        }
                        categoryItemState.addAll(categoriesVo)
                    }
                }
            }
        }
    }

    fun signUp(bossName: String, storeName: String, businessNumber: String, certificationPhotoRequestBody: RequestBody?) {
        viewModelScope.launch(exceptionHandler) {
            if (certificationPhotoRequestBody != null) {
                imageUploadUseCase.postImageUpload("BOSS_STORE_CERTIFICATION_IMAGE", certificationPhotoRequestBody).collect { it ->
                    if (it.code == "200") {
                        val accessToken = TokenManager.instance.getToken()?.accessToken
                        if (accessToken == null) {
                            // TODO: 토큰이 없을때 처리
                            return@collect
                        }
                        if (businessNumber.length != 10) {
                            return@collect
                            // TODO: 사업자 등록 번호 이상할 때
                        }
                        var businessNumberHyphen = ""
                        businessNumber.forEachIndexed { index, c ->
                            businessNumberHyphen += c
                            if (index == 2 || index == 4) {
                                businessNumberHyphen += "-"
                            }

                        }
                        authUseCase.signUp(
                            bossName = bossName,
                            businessNumber = businessNumberHyphen,
                            certificationPhotoUrl = it.data?.imageUrl.toString(),
                            socialType = "KAKAO",
                            storeCategoriesIds = selectedItems.map { selectedItem -> selectedItem.categoryId },
                            storeName = storeName,
                            token = accessToken
                        ).collect { result ->
                            val token = result.data?.token
                            if (!token.isNullOrEmpty()) {
                                authUseCase.saveAccessToken(token).collect {
                                    _loginNavItem.emit(LoginNavItem.Waiting)
                                }
                            } else {
                                // TODO: 토큰이 비어있거나 Null일때
                            }
                            // TODO: 에러 메시지 처리 result.errorMessage
                        }
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
            selectedItems.remove(item)
        } else if (selectedItems.size < 3) {
            categoryItemState[index] = item.copy(isSelected = true)
            selectedItems.add(categoryItemState[index])
        }
    }
}