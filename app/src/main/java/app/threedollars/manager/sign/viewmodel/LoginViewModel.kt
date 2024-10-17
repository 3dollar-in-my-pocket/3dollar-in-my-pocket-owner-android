package app.threedollars.manager.sign.viewmodel

import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.common.EventFlow
import app.threedollars.common.MutableEventFlow
import app.threedollars.domain.usecase.AuthUseCase
import app.threedollars.domain.usecase.BossAccountUseCase
import app.threedollars.domain.usecase.BossDeviceUseCase
import app.threedollars.manager.sign.LoginNavItem
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val bossAccountUseCase: BossAccountUseCase,
    private val bossDeviceUseCase: BossDeviceUseCase,
) : BaseViewModel() {

    private val _loginNavItem = MutableEventFlow<LoginNavItem>()
    val loginNavItem: EventFlow<LoginNavItem> get() = _loginNavItem
    fun login(accessToken: String) {
        viewModelScope.launch(exceptionHandler) {
            authUseCase.login("KAKAO", accessToken).collect { loginDto ->
                authUseCase.saveSocialAccessToken(accessToken).collect {
                    if (loginDto.code.toString() == "200") {
                        loginDto.data?.token?.let { token ->
                            authUseCase.saveAccessToken(token).collect {
                                checkMyInfo()
                            }
                        }
                    } else if (loginDto.code.toString() == "404") {
                        _loginNavItem.emit(LoginNavItem.Sign)
                    }
                }
            }
        }
    }

    private fun checkMyInfo() {
        viewModelScope.launch(exceptionHandler) {
            bossAccountUseCase.getBossAccount().collect {
                if (it.code.toString() == "200") {
                    val firebaseToken = async { FirebaseMessaging.getInstance().token.await() }
                    bossDeviceUseCase.putBossDeviceToken("FCM", firebaseToken.await()).collect {
                        _loginNavItem.emit(LoginNavItem.Home)
                    }
                } else if (it.code.toString() == "403") {
                    _loginNavItem.emit(LoginNavItem.Waiting)
                } else if (it.code.toString() == "404") {
                    _loginNavItem.emit(LoginNavItem.Sign)
                }
            }
        }
    }

}