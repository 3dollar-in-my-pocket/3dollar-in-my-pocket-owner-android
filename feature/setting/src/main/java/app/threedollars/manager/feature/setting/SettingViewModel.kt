package app.threedollars.manager.feature.setting

import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.domain.usecase.AuthUseCase
import app.threedollars.domain.usecase.BossAccountUseCase
import app.threedollars.domain.usecase.BossDeviceUseCase
import app.threedollars.domain.usecase.FaqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val bossAccountUseCase: BossAccountUseCase,
    private val faqUseCase: FaqUseCase,
    private val bossDeviceUseCase: BossDeviceUseCase,
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())

    val stateFlow: StateFlow<SettingState> = _stateFlow.asStateFlow()

    init {
        getBossAccount()
        getFaq()
    }

    private fun getBossAccount() {
        viewModelScope.launch {
            bossAccountUseCase.getBossAccount().collect {
                if (it.code == "200") {
                    it.data?.let { dto ->
                        _stateFlow.update { state ->
                            state.copy(bossAccountInfo = dto)
                        }
                    }
                }
            }
        }
    }

    fun updatePageType(pageType: PageType) {
        viewModelScope.launch {
            _stateFlow.update { state ->
                state.copy(
                    pageType = pageType
                )
            }
        }
    }

    private fun getFaq() {
        viewModelScope.launch {
            faqUseCase.getFaqs().collect {
                if (it.code == "200") {
                    it.data?.let { dtoList ->
                        _stateFlow.update { state ->
                            state.copy(
                                faqList = dtoList.sortedBy { dto -> dto.categoryInfo.displayOrder }
                            )
                        }
                    }
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCase.signOut().collect {
                if (it.data == "OK") {

                    val accessTokenDeferred = async { authUseCase.saveAccessToken("") }
                    val socialAccessTokenDeferred = async { authUseCase.saveSocialAccessToken("") }

                    awaitAll(accessTokenDeferred, socialAccessTokenDeferred).forEach { flow ->
                        flow.collect()
                    }
                    _stateFlow.update { state ->
                        state.copy(
                            isSuccess = true
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authUseCase.logout().collect {
                if (it.data == "OK") {
                    val accessTokenDeferred = async { authUseCase.saveAccessToken("") }
                    val socialAccessTokenDeferred = async { authUseCase.saveSocialAccessToken("") }

                    awaitAll(accessTokenDeferred, socialAccessTokenDeferred).forEach { flow ->
                        flow.collect()
                    }
                    _stateFlow.update { state ->
                        state.copy(
                            isSuccess = true
                        )
                    }
                }
            }
        }
    }

    fun putBossDevice(token: String) {
        viewModelScope.launch {
            bossDeviceUseCase.putBossDevice("FCM", token).collect {
                // TODO: 푸시 알림 설정 완료 / 실패 핸들링
            }
        }
    }

    fun deleteBossDevice() {
        viewModelScope.launch {
            bossDeviceUseCase.deleteBossDevice().collect {
                // TODO: 푸시 알림 해제 완료 / 실패 핸들링
            }
        }
    }
}