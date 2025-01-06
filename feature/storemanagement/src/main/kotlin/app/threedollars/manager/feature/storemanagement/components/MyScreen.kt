package app.threedollars.manager.feature.storemanagement.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import app.threedollars.manager.feature.storemanagement.R
import app.threedollars.manager.feature.storemanagement.ScreenType
import app.threedollars.manager.feature.storemanagement.model.AccountNumbersVo
import app.threedollars.manager.feature.storemanagement.model.BossStoreRetrieveVo
import app.threedollars.manager.feature.storemanagement.model.BusinessScheduleModel
import app.threedollars.manager.feature.storemanagement.model.MenuModel
import app.threedollars.manager.feature.storemanagement.model.ProfileModel
import app.threedollars.manager.feature.storemanagement.model.TitleModel
import app.threedollars.manager.feature.storemanagement.model.toBusinessSchedule
import app.threedollars.manager.feature.storemanagement.toWon
import coil.compose.AsyncImage

@Composable
internal fun MyScreen(
    bossStoreRetrieve: BossStoreRetrieveVo,
    onScreenTypeUpdate: (ScreenType) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .verticalScroll(scrollState),
    ) {
            val introduction = bossStoreRetrieve.introduction.ifEmpty { "손님들에게 감동을 드릴 한마디를 적어주세요!ex) 오전에 오시면 서비스가 있습니다!" }
            DisplayProfileInfo(
                profile = ProfileModel(
                    image = bossStoreRetrieve.imageUrl,
                    name = bossStoreRetrieve.name,
                    category = bossStoreRetrieve.categories.map { it.name.toStringDefault() },
                    snsLink = bossStoreRetrieve.snsUrl
                ),
                editClick = {
                    onScreenTypeUpdate(ScreenType.PROFILE_EDIT)
                }
            )
            Spacer(modifier = Modifier.height(44.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                TitleContents(
                    bottomPadding = 12.dp,
                    TitleModel(
                        title = "사장님 한마디",
                        buttonName = "정보수정",
                        buttonClick = {
                            onScreenTypeUpdate(ScreenType.BOSS_COMMENT)
                        }
                    ),
                )
                Text(
                    text = introduction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    fontSize = 14.sp,
                    color = if (bossStoreRetrieve.introduction.isEmpty()) Gray40 else Gray95,
                )
                Spacer(modifier = Modifier.height(37.dp))
                TitleContents(
                    bottomPadding = 16.dp,
                    title = TitleModel(
                        title = "메뉴 정보",
                        buttonName = "메뉴 관리",
                        buttonClick = {
                            onScreenTypeUpdate(ScreenType.MENU_MANAGEMENT)
                        }
                    ),
                )
                MenuContents(
                    bossStoreRetrieve.menus.map { menu ->
                        MenuModel(
                            name = menu.name.toStringDefault(),
                            imageUrl = menu.imageUrl.toStringDefault(),
                            price = menu.price.toIntDefault(),
                        )
                    },
                )
                Spacer(modifier = Modifier.height(40.dp))
                TitleContents(
                    bottomPadding = 12.dp,
                    title = TitleModel(
                        title = "계좌정보",
                        buttonName = "정보 수정",
                        buttonClick = {
                            onScreenTypeUpdate(ScreenType.ACCOUNT)
                        }
                    ),
                )
                AccountContents(
                    accountNumber = bossStoreRetrieve.accountNumbers.firstOrNull() ?: AccountNumbersVo()
                )
                Spacer(modifier = Modifier.height(36.dp))
                TitleContents(
                    bottomPadding = 16.dp,
                    title = TitleModel(
                        title = "영업 일정",
                        buttonName = "일정 관리",
                        buttonClick = {
                            onScreenTypeUpdate(ScreenType.BUSINESS_SCHEDULE_EDIT)
                        }
                    ),
                )
                val businessSchedules = emptyBusinessSchedules.toMutableList()
                bossStoreRetrieve.appearanceDays.map { dto ->
                    dto.toBusinessSchedule()
                }.forEach { businessScheduleModel ->
                    val index = businessSchedules.indexOfFirst { it.dayOfTheWeek == businessScheduleModel.dayOfTheWeek }
                    businessSchedules[index] = businessScheduleModel
                }
                BusinessScheduleContents(businessSchedules)
                Spacer(modifier = Modifier.height(64.dp))
            }
    }
}

@Composable
private fun AccountContents(accountNumber: AccountNumbersVo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
    ) {
        Text(
            text = "${accountNumber.bankVo.description} ${accountNumber.accountNumber}",
            modifier = Modifier
                .wrapContentSize(),
            fontSize = 14.sp,
            color = Gray95,
        )
        Text(
            text = " ${accountNumber.accountHolder}",
            modifier = Modifier
                .wrapContentSize(),
            fontSize = 14.sp,
            color = Gray95,
        )
    }
}

@Composable
private fun DisplayProfileInfo(
    profile: ProfileModel,
    editClick: () -> Unit = {},
) {
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
private fun ProfileContents(
    profile: ProfileModel,
    editClick: () -> Unit,
) {
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
            Text(
                text = "SNS",
                modifier = Modifier.defaultMinSize(44.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(41.dp))
            Text(
                text = profile.snsLink,
                color = Gray50,
                fontSize = 12.sp,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "대표 정보 수정",
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    color = colorResource(id = R.color.green500),
                    shape = RoundedCornerShape(8.dp)
                )
                .wrapContentHeight(Alignment.CenterVertically)
                .clickable { editClick() },
        )
    }
}

@Composable
private fun CategoryItem(category: String) {
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

@Composable
private fun TitleContents(
    bottomPadding: Dp = 16.dp,
    title: TitleModel,
) {
    Row(
        modifier = Modifier
            .padding(bottom = bottomPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title.title,
            color = Gray95,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = title.buttonName,
            modifier = Modifier.clickable { title.buttonClick() },
            color = Green,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun MenuContents(menus: List<MenuModel> = listOf()) {
    if (menus.isEmpty()) {
        MenuEmptyItem()
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            menus.forEach { MenuItem(it) }
        }
    }
}

@Composable
internal fun MenuItem(
    menu: MenuModel,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = menu.name.toStringDefault(),
                fontSize = 14.sp,
                color = Gray95,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = menu.price.toIntDefault().toWon(),
                fontSize = 14.sp,
                color = Gray95
            )
        }
        AsyncImage(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            model = menu.imageUrl,
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
            .background(
                color = White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "가게의 메뉴를 등록해주세요!",
            fontSize = 14.sp,
            color = Gray40
        )
        Image(
            painter = painterResource(id = R.drawable.ic_empty_menu),
            contentDescription = "메뉴 기본 아이콘"
        )
    }
}

@Composable
internal fun BusinessScheduleContents(
    businessSchedules: List<BusinessScheduleModel>,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        businessSchedules.forEach {
            BusinessScheduleItem(it)
        }
    }
}

@Composable
internal fun BusinessScheduleItem(
    businessSchedule: BusinessScheduleModel,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = businessSchedule.dayOfTWeek,
            fontSize = 14.sp,
            color = if (businessSchedule.isWeekend) Red else Gray95
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = businessSchedule.openingHours,
                fontSize = 16.sp,
                color = if (businessSchedule.isOpen) Gray70 else Red,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = businessSchedule.locationDescription,
                fontSize = 14.sp,
                color = Gray70
            )
        }
    }
}

internal val emptyBusinessSchedules = listOf(
    BusinessScheduleModel("월요일", "MONDAY", "-", "휴무", false, isWeekend = false),
    BusinessScheduleModel("화요일", "TUESDAY", "-", "휴무", false, isWeekend = false),
    BusinessScheduleModel("수요일", "WEDNESDAY", "-", "휴무", false, isWeekend = false),
    BusinessScheduleModel("목요일", "THURSDAY", "-", "휴무", false, isWeekend = false),
    BusinessScheduleModel("금요일", "FRIDAY", "-", "휴무", false, isWeekend = false),
    BusinessScheduleModel("토요일", "SATURDAY", "-", "휴무", false, isWeekend = true),
    BusinessScheduleModel("일요일", "SUNDAY", "-", "휴무", false, isWeekend = true),
)