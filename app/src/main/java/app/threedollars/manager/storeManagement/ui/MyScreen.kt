package app.threedollars.manager.storeManagement.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.threedollars.common.ext.toIntDefault
import app.threedollars.common.ext.toStringDefault
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray100
import app.threedollars.common.ui.Gray40
import app.threedollars.common.ui.Gray50
import app.threedollars.common.ui.Gray70
import app.threedollars.common.ui.Gray95
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.Green_OP10
import app.threedollars.common.ui.Red
import app.threedollars.common.ui.White
import app.threedollars.manager.R
import app.threedollars.manager.storeManagement.ui.account.AccountActivity
import app.threedollars.manager.storeManagement.ui.businessschedule.BusinessScheduleEditActivity
import app.threedollars.manager.storeManagement.ui.profile.ProfileEditActivity
import app.threedollars.manager.storeManagement.viewModel.MyViewModel
import app.threedollars.manager.vo.AccountNumbersVo
import app.threedollars.manager.vo.AppearanceDaysVo
import coil.compose.AsyncImage

@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
    viewModel.getBossStoreRetrieveMe()
    val scrollState = rememberScrollState()
    val bossStore = viewModel.bossStoreRetrieveMe.collectAsState(null)
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        when (it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                viewModel.getBossStoreRetrieveMe()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .verticalScroll(scrollState),
    ) {
        bossStore.value?.let {
            val introduction = it.introduction.ifEmpty { "손님들에게 감동을 드릴 한마디를 적어주세요!ex) 오전에 오시면 서비스가 있습니다!" }
            displayProfileInfo(Profile(it.imageUrl, it.name, it.categories.map { it.name.toStringDefault() }, it.snsUrl)) {
                launcher.launch(Intent(context, ProfileEditActivity::class.java))
            }
            Spacer(modifier = Modifier.height(44.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                TitleContents(
                    bottomPadding = 12.dp,
                    Title("사장님 한마디", "정보수정") {
                        launcher.launch(Intent(context, BossCommentActivity::class.java))
                    },
                )
                Text(
                    text = introduction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    fontSize = 14.sp,
                    color = if (it.introduction.isEmpty()) Gray40 else Gray95,
                )
                Spacer(modifier = Modifier.height(37.dp))
                TitleContents(
                    bottomPadding = 16.dp,
                    Title("메뉴 정보", "메뉴 관리") {
                        launcher.launch(Intent(context, MenuManagementActivity::class.java))
                    },
                )
                MenuContents(
                    it.menus.map { menu ->
                        Menu(
                            menu.name.toStringDefault(),
                            menu.imageUrl.toStringDefault(),
                            menu.price.toIntDefault().toWon(),
                        )
                    },
                )
                Spacer(modifier = Modifier.height(40.dp))
                TitleContents(
                    bottomPadding = 12.dp,
                    Title("계좌정보", "정보 수정") {
                        launcher.launch(Intent(context, AccountActivity::class.java))
                    },
                )
                AccountContents(bossStore.value?.accountNumbers?.firstOrNull() ?: AccountNumbersVo())
                Spacer(modifier = Modifier.height(36.dp))
                TitleContents(
                    bottomPadding = 16.dp,
                    Title("영업 일정", "일정 관리") {
                        launcher.launch(Intent(context, BusinessScheduleEditActivity::class.java))
                    },
                )
                val businessSchedules = mutableListOf<BusinessSchedule>()
                businessSchedules.addAll(emptyBusinessSchedules)
                it.appearanceDays.map { dto -> dto.toBusinessSchedule() }.forEach { businessSchedule ->
                    val index = businessSchedules.indexOfFirst { it.dayOfTheWeek == businessSchedule.dayOfTheWeek }
                    businessSchedules[index] = businessSchedule
                }
                BusinessScheduleContents(businessSchedules)
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun AccountContents(accountNumber: AccountNumbersVo?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
    ) {
        Text(
            text = "${accountNumber?.bankVo?.description} ${accountNumber?.accountNumber}",
            modifier = Modifier
                .wrapContentSize(),
            fontSize = 14.sp,
            color = Gray95,
        )
        Text(
            text = " ${accountNumber?.accountHolder}",
            modifier = Modifier
                .wrapContentSize(),
            fontSize = 14.sp,
            color = Gray95,
        )
    }
}

@Preview
@Composable
fun displayProfileInfo(profile: Profile = emptyProfile, editClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = profile.image,
            contentDescription = "대표 사진",
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Crop,
        )
        ProfileContents(profile, editClick)
    }
}

@Composable
fun ProfileContents(profile: Profile, editClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 210.dp)
            .shadow(8.dp)
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = profile.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Gray100,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        )
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(profile.category) {
                CategoryItem(it)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray0, shape = RoundedCornerShape(12.dp))
                .padding(12.dp),
        ) {
            Text(text = "SNS", modifier = Modifier.defaultMinSize(44.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(41.dp))
            Text(text = profile.snsLink, color = Gray50, fontSize = 12.sp, textAlign = TextAlign.End)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "대표 정보 수정",
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(colorResource(id = R.color.green500), shape = RoundedCornerShape(8.dp))
                .wrapContentHeight(Alignment.CenterVertically)
                .clickable { editClick() },
        )
    }
}

@Composable
fun CategoryItem(category: String) {
    Text(
        text = category,
        modifier = Modifier
            .background(color = Green_OP10, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = Green,
        fontSize = 14.sp,
        style = TextStyle(
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
    )
}

@Preview
@Composable
fun TitleContents(bottomPadding: Dp = 16.dp, title: Title = emptyTitle) {
    Row(
        modifier = Modifier
            .padding(bottom = bottomPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title.title, color = Gray95, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = title.buttonName, modifier = Modifier.clickable { title.buttonClick() }, color = Green, fontSize = 12.sp)
    }
}

@Preview
@Composable
fun MenuContents(menus: List<Menu> = listOf()) {
    if (menus.isEmpty()) {
        MenuEmptyItem()
    } else {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            menus.forEach { MenuItem(it) }
        }
    }
}

@Preview
@Composable
fun MenuItem(menu: Menu = emptyMenu) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(text = menu.menuName, fontSize = 14.sp, color = Gray95, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = menu.menuPrice, fontSize = 14.sp, color = Gray95)
        }
        AsyncImage(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            model = menu.menuImage,
            contentDescription = "메뉴 이미지",
            placeholder = painterResource(id = R.drawable.ic_empty_menu),
        )
    }
}

@Preview
@Composable
fun MenuEmptyItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "가게의 메뉴를 등록해주세요!", fontSize = 14.sp, color = Gray40)
        Image(painter = painterResource(id = R.drawable.ic_empty_menu), contentDescription = "메뉴 기본 아이콘")
    }
}

@Preview
@Composable
fun BusinessScheduleContents(businessSchedules: List<BusinessSchedule> = emptyBusinessSchedules) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        businessSchedules.forEach {
            BusinessScheduleItem(it)
        }
    }
}

@Preview
@Composable
fun BusinessScheduleItem(businessSchedule: BusinessSchedule = emptyBusinessSchedules[0]) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = businessSchedule.dayOfTWeek, fontSize = 14.sp, color = if (businessSchedule.isWeekend) Red else Gray95)
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = businessSchedule.openingHours,
                fontSize = 16.sp,
                color = if (businessSchedule.isOpen) Gray70 else Red,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = businessSchedule.locationDescription, fontSize = 14.sp, color = Gray70)
        }
    }
}

data class BusinessSchedule(
    val dayOfTWeek: String,
    val dayOfTheWeek: String,
    val locationDescription: String,
    val openingHours: String,
    val isOpen: Boolean,
    val isWeekend: Boolean,
)

val emptyBusinessSchedules = listOf(
    BusinessSchedule("월요일", "MONDAY", "-", "휴무", false, isWeekend = false),
    BusinessSchedule("화요일", "TUESDAY", "-", "휴무", false, isWeekend = false),
    BusinessSchedule("수요일", "WEDNESDAY", "-", "휴무", false, isWeekend = false),
    BusinessSchedule("목요일", "THURSDAY", "-", "휴무", false, isWeekend = false),
    BusinessSchedule("금요일", "FRIDAY", "-", "휴무", false, isWeekend = false),
    BusinessSchedule("토요일", "SATURDAY", "-", "휴무", false, isWeekend = true),
    BusinessSchedule("일요일", "SUNDAY", "-", "휴무", false, isWeekend = true),
)

class Menu(
    val menuName: String,
    val menuImage: String,
    val menuPrice: String,
)

val emptyMenu = Menu("아저씨 못난이 핫도그", "", "5,000원")

data class Title(
    val title: String,
    val buttonName: String,
    val buttonClick: () -> Unit,
)

val emptyTitle = Title("사장님 한마디", "정보 수정") {}

data class Profile(
    val image: String,
    val name: String,
    val category: List<String>,
    val snsLink: String,
)

val emptyProfile = Profile(
    "https://developer.android.com/static/images/jetpack/compose/layout-jetnews-scaffold.png?hl=ko",
    "ssssssssss",
    listOf("게임", "독서", "운동"),
    "instagram.com/3dollar_in_my_pocket?utm_medium=copy_link",
)

fun Int.toWon(): String {
    val formatter = java.text.DecimalFormat("#,###")
    return "${formatter.format(this)}원"
}

private fun AppearanceDaysVo.toBusinessSchedule(): BusinessSchedule {
    val openingHours = openingHours.let {
        "${it.startTime.toStringDefault()} - ${it.endTime.toStringDefault()}"
    }
    val dayOfTheWeek = dayOfTheWeek.toStringDefault()
    val dayOfTWeek = emptyBusinessSchedules.find { it.dayOfTheWeek == dayOfTheWeek }?.dayOfTWeek.toStringDefault()
    val isWeekend = dayOfTWeek == "일요일" || dayOfTWeek == "토요일"
    return BusinessSchedule(dayOfTWeek, dayOfTheWeek, locationDescription.toStringDefault(), openingHours, openingHours != "휴무", isWeekend)
}
