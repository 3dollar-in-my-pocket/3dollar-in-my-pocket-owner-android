package app.threedollars.manager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.threedollars.manager.sign.LoginNavItem
import app.threedollars.manager.sign.ui.LoginScreen
import app.threedollars.manager.sign.ui.SignScreen
import app.threedollars.manager.sign.ui.SplashScreen
import app.threedollars.manager.sign.ui.WaitingScreen
import app.threedollars.manager.sign.viewmodel.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginNavigationGraph(loginViewModel) {
                loginKakao()
            }
        }
    }

    private fun loginKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                loginViewModel.login(token.accessToken)
                Log.i("TAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("kakao", "카카오톡으로 로그인 실패", error)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    loginViewModel.login(token.accessToken)
                    Log.i("TAG", "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
}

@Composable
fun LoginNavigationGraph(viewModel: LoginViewModel, loginKakao: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController, startDestination = LoginNavItem.Splash.screenRoute, modifier = Modifier.fillMaxSize()
    ) {
        composable(LoginNavItem.Splash.screenRoute) {
            SplashScreen(navController)
        }

        composable(LoginNavItem.Login.screenRoute) {
            LoginScreen(navController, loginKakao, viewModel)
        }
        composable(LoginNavItem.Sign.screenRoute) {
            SignScreen(navController)
        }
        composable(LoginNavItem.Waiting.screenRoute) {
            WaitingScreen(navController)
        }
    }
}