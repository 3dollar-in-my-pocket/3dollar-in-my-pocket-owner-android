package app.threedollars.manager.setting

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.threedollars.common.BaseDialog
import app.threedollars.manager.LoginActivity
import app.threedollars.manager.R
import app.threedollars.manager.sign.viewmodel.SettingViewModel
import app.threedollars.manager.util.findActivity
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val openWebPage = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .verticalScroll(scrollState)
    ) {
        viewModel.getBossAccount()

        val context = LocalContext.current

        val bossAccountInfo by viewModel.bossAccountInfo.collectAsStateWithLifecycle(null)
        val isSuccess by viewModel.isSuccess.collectAsStateWithLifecycle(false)
        var isSignOutDialog by remember { mutableStateOf(false) }
        var switchOn by remember { mutableStateOf(false) }

        LaunchedEffect(bossAccountInfo) {
            switchOn = bossAccountInfo?.isSetupNotification == true
        }

        if (isSignOutDialog) {
            BaseDialog(
                title = "회원탈퇴",
                message = "회원 탈퇴 시, 그동안의 데이터가\n모두 삭제됩니다.\n회원탈퇴하시겠습니까?",
                confirmText = "탈퇴",
                dismissText = "취소",
                onConfirm = {
                    viewModel.signOut()
                },
                onDismiss = { isSignOutDialog = false })
        }
        Text(
            text = stringResource(R.string.setting),
            fontWeight = FontWeight.W600,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(R.string.boss_name, bossAccountInfo?.name.toString()),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(start = 24.dp, top = 36.dp),
            fontSize = 24.sp
        )
        Text(
            text = stringResource(R.string.setting_title),
            fontWeight = FontWeight.Normal,
            color = Color.White,
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp),
            fontSize = 24.sp
        )
        SettingCategoryContent(
            leftText = stringResource(R.string.business_number),
            rightText = bossAccountInfo?.businessNumber,
            rightTextColor = R.color.gray30,
            modifier = Modifier.padding(top = 20.dp)
        )
        Box(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.gray90))
                .padding(vertical = 5.dp, horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "푸시알림",
                fontWeight = FontWeight.W600,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

            Switch(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = switchOn,
                onCheckedChange = { isSwitch ->
                    switchOn = isSwitch
                    if (isSwitch) {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener {
                            viewModel.putBossDevice(it.result)
                        }
                    } else {
                        viewModel.deleteBossDevice()
                    }
                }
            )
        }
        SettingCategoryContent(
            leftText = stringResource(R.string.call), rightImage = R.drawable.ic_right_arrow,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_RxghUb/chat"))
                openWebPage.launch(intent)
            }
        )
        SettingCategoryContent(
            leftText = stringResource(id = R.string.faq), rightImage = R.drawable.ic_right_arrow,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                navController.navigate(SettingNavItem.Faq.screenRoute)
            }
        )
        SettingCategoryContent(
            leftText = stringResource(R.string.privacy_policy), rightImage = R.drawable.ic_right_arrow,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pool-battery-996.notion.site/3-3d0a9c55ddd74086b63582c308ca285e"))
                openWebPage.launch(intent)

            }
        )
        SettingCategoryContent(
            leftImage = if (bossAccountInfo?.socialType == "KAKAO") R.drawable.ic_logo_kakao else R.drawable.ic_logo_naver,
            leftText = if (bossAccountInfo?.socialType == "KAKAO") stringResource(R.string.kakao_account_member) else stringResource(R.string.naver_account_member),
            rightText = stringResource(R.string.logout),
            rightTextColor = R.color.red500,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                viewModel.logout()
            }
        )
        Row(
            Modifier
                .padding(start = 24.dp, top = 24.dp)
                .clickable { isSignOutDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_alert_circle),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.signout),
                color = colorResource(id = R.color.gray40)
            )
        }
        LaunchedEffect(isSuccess) {
            if (isSuccess) {
                context.startActivity(Intent(context, LoginActivity::class.java))
                context.findActivity().finish()
            }
        }
    }
}