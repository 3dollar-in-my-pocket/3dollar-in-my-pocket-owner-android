package app.threedollars.manager.feature.setting

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.BaseDialog
import app.threedollars.domain.dto.BossAccountInfoDto
import app.threedollars.manager.feature.setting.components.SettingCategoryContent
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun SettingScreen(
    bossAccountInfo: BossAccountInfoDto,
    onClickSignOut: () -> Unit,
    onClickLogOut: () -> Unit,
    onClickFaq: (PageType) -> Unit,
    onSwitchBossDevice: (Boolean, String) -> Unit,
) {
    val openWebPage = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .verticalScroll(scrollState)
    ) {
        var isSignOutDialog by remember { mutableStateOf(false) }
        var switchOn by remember { mutableStateOf(bossAccountInfo.isSetupNotification) }

        if (isSignOutDialog) {
            BaseDialog(
                title = "회원탈퇴",
                message = "회원 탈퇴 시, 그동안의 데이터가\n모두 삭제됩니다.\n회원탈퇴하시겠습니까?",
                confirmText = "탈퇴",
                dismissText = "취소",
                onConfirm = {
                    onClickSignOut()
                },
                onDismiss = { isSignOutDialog = false })
        }
        Text(
            text = "설정",
            fontWeight = FontWeight.W600,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(R.string.boss_name, bossAccountInfo.name),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(start = 24.dp, top = 36.dp),
            fontSize = 24.sp
        )
        Text(
            text = "오늘도 적게 일하고 많이 버세요!",
            fontWeight = FontWeight.Normal,
            color = Color.White,
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp),
            fontSize = 24.sp
        )
        SettingCategoryContent(
            leftText = "사업자 번호",
            rightText = bossAccountInfo.businessNumber,
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
                            onSwitchBossDevice(
                                true,
                                it.result
                            )
                        }
                    } else {
                        onSwitchBossDevice(
                            false,
                            ""
                        )
                    }
                }
            )
        }
        SettingCategoryContent(
            leftText = "가슴속 삼천원팀에 연락하기", rightImage = R.drawable.ic_right_arrow,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_RxghUb/chat"))
                openWebPage.launch(intent)
            }
        )
        SettingCategoryContent(
            leftText = "FAQ", rightImage = R.drawable.ic_right_arrow,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                onClickFaq(PageType.FAQ)
            }
        )
        SettingCategoryContent(
            leftText = "개인정보 처리방침", rightImage = R.drawable.ic_right_arrow,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pool-battery-996.notion.site/3-3d0a9c55ddd74086b63582c308ca285e"))
                openWebPage.launch(intent)

            }
        )
        SettingCategoryContent(
            leftImage = if (bossAccountInfo.socialType == "KAKAO") R.drawable.ic_logo_kakao else R.drawable.ic_logo_naver,
            leftText = if (bossAccountInfo.socialType == "KAKAO") "카카오 계정 회원" else "네이버 계정 회원",
            rightText = stringResource(R.string.logout),
            rightTextColor = R.color.red500,
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                onClickLogOut()
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
                text = "회원탈퇴",
                color = colorResource(id = R.color.gray40)
            )
        }
    }
}