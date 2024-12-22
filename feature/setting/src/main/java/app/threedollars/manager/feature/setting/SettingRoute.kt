package app.threedollars.manager.feature.setting

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingRoute(
    onMoveLoginPage: () -> Unit,
) {
    val viewModel: SettingViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onMoveLoginPage()
        }
    }

    when (uiState.pageType) {
        PageType.SETTING -> {
            SettingScreen(
                bossAccountInfo = uiState.bossAccountInfo,
                onClickSignOut = { viewModel.signOut() },
                onClickLogOut = { viewModel.logout() },
                onSwitchBossDevice = { isSwitch, result ->
                    if (isSwitch) {
                        viewModel.putBossDevice(result)
                    } else {
                        viewModel.deleteBossDevice()
                    }
                },
                onClickFaq = { pageType ->
                    viewModel.updatePageType(pageType = pageType)
                }
            )
        }

        PageType.FAQ -> {
            FaqScreen(
                faqList = uiState.faqList,
                onClickBackButton = { pageType ->
                    viewModel.updatePageType(pageType = pageType)
                }
            )
        }
    }
}