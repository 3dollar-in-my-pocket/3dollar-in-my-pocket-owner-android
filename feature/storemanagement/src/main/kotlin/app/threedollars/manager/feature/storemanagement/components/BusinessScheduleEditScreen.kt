package app.threedollars.manager.feature.storemanagement.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ui.Black
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray10
import app.threedollars.common.ui.Gray100
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray40
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.Red
import app.threedollars.common.ui.White
import app.threedollars.domain.dto.AppearanceDaysRequestDto
import app.threedollars.manager.feature.storemanagement.R
import app.threedollars.manager.feature.storemanagement.ScreenType
import app.threedollars.manager.feature.storemanagement.defaultScheduleDays
import app.threedollars.manager.feature.storemanagement.model.AppearanceDaysVo
import app.threedollars.manager.feature.storemanagement.model.BossStorePatchModel
import app.threedollars.manager.feature.storemanagement.model.BossStoreRetrieveVo
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
internal fun BusinessScheduleEditScreen(
    bossStoreRetrieve: BossStoreRetrieveVo,
    scheduleDays: List<ScheduleDay>,
    appearanceDays: HashMap<String, AppearanceDaysVo>,
    onScreenTypeUpdate: (ScreenType) -> Unit,
    onBossStorePatch: (BossStorePatchModel) -> Unit,
    onStartTimeUpdate: (String, String) -> Unit,
    onEndTimeUpdate: (String, String) -> Unit,
    onLocationDescriptionUpdate: (String, String) -> Unit,
    onScheduleDayUpdate: (ScheduleDay) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray0),
    ) {
        BusinessScheduleEditTop(
            onScreenTypeUpdate = onScreenTypeUpdate
        )
        BusinessScheduleContents(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            scheduleDays = scheduleDays,
            onStartTimeUpdate = onStartTimeUpdate,
            onEndTimeUpdate = onEndTimeUpdate,
            onLocationDescriptionUpdate = onLocationDescriptionUpdate,
            onScheduleDayUpdate = onScheduleDayUpdate
        )
        BusinessScheduleBottom(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            isEnable = appearanceDays.values.all {
                it.openingHours.startTime.isNotEmpty() && it.openingHours.endTime.isNotEmpty()
            },
            onClick = {
                onBossStorePatch(
                    BossStorePatchModel(
                        bossStoreId = bossStoreRetrieve.bossStoreId,
                        appearanceDays = appearanceDays.values.map {
                            AppearanceDaysRequestDto(
                                dayOfTheWeek = it.dayOfTheWeek,
                                startTime = it.openingHours.startTime,
                                endTime = it.openingHours.endTime,
                                locationDescription = it.locationDescription,
                            )
                        }
                    )
                )
            }
        )
    }
}

@Composable
private fun BusinessScheduleEditTop(
    onScreenTypeUpdate: (ScreenType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 16.dp),
            onClick = {
                onScreenTypeUpdate(ScreenType.STORE_INFO)
            },
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_back
                ),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = "일정 관리",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun BusinessScheduleContents(
    modifier: Modifier = Modifier,
    scheduleDays: List<ScheduleDay>,
    onStartTimeUpdate: (String, String) -> Unit,
    onEndTimeUpdate: (String, String) -> Unit,
    onLocationDescriptionUpdate: (String, String) -> Unit,
    onScheduleDayUpdate: (ScheduleDay) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        BusinessScheduleSelectDay(
            scheduleDays = scheduleDays,
            onScheduleDayUpdate = onScheduleDayUpdate
        )
        Spacer(modifier = Modifier.height(48.dp))
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            scheduleDays.forEach { scheduleDay ->
                if (scheduleDay.isSelected) {
                    BusinessScheduleDayDetail(
                        scheduleDay = scheduleDay,
                        startTimeChanged = { startTime ->
                            onStartTimeUpdate(scheduleDay.dayOfTheWeek, startTime)
                        },
                        endTimeChanged = { endTime ->
                            onEndTimeUpdate(scheduleDay.dayOfTheWeek, endTime)
                        },
                        locationDescriptionChanged = { locationDescription ->
                            onLocationDescriptionUpdate(scheduleDay.dayOfTheWeek, locationDescription)
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Composable
private fun BusinessScheduleSelectDay(
    scheduleDays: List<ScheduleDay>,
    onScheduleDayUpdate: (ScheduleDay) -> Unit,
) {
    val title = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("영업 요일")
        }
        append("을 선택해주세요!")
    }
    val explanation = buildAnnotatedString {
        append("선택하지 않은 요일은 ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Red)) {
            append("휴무")
        }
        append("로 표시됩니다.")
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = explanation, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        BusinessScheduleDays(
            scheduleDays = scheduleDays,
            onScheduleDayUpdate = onScheduleDayUpdate
        )
    }
}

@Composable
private fun BusinessScheduleDays(
    scheduleDays: List<ScheduleDay>,
    onScheduleDayUpdate: (ScheduleDay) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        scheduleDays.forEach {
            BusinessScheduleDay(
                scheduleDay = it,
                clickDay = onScheduleDayUpdate
            )
        }
    }
}

@Composable
private fun BusinessScheduleDay(
    scheduleDay: ScheduleDay,
    clickDay: (ScheduleDay) -> Unit = {},
) {
    val isSelected = scheduleDay.isSelected
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Black else Gray5)
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = if (isSelected) Black else Gray10,
                shape = CircleShape
            )
            .clickable {
                val day = if (isSelected) {
                    scheduleDay.copy(
                        startTime = "",
                        endTime = "",
                        locationDescription = "",
                        isSelected = false
                    )
                } else {
                    scheduleDay.copy(isSelected = true)
                }
                clickDay(day)
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = scheduleDay.dayOfWeek,
            textAlign = TextAlign.Center,
            color = if (isSelected) White else Gray40,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        )
    }
}

@Composable
private fun BusinessScheduleDayDetail(
    scheduleDay: ScheduleDay = defaultScheduleDays[0],
    startTimeChanged: (String) -> Unit = {},
    endTimeChanged: (String) -> Unit = {},
    locationDescriptionChanged: (String) -> Unit = {},
) {
    var startTime by remember(scheduleDay) { mutableStateOf(scheduleDay.startTime.ifEmpty { "시작시간" }) }
    var endTime by remember(scheduleDay) { mutableStateOf(scheduleDay.endTime.ifEmpty { "종료시간" }) }
    var locationDescription by remember(scheduleDay) { mutableStateOf(scheduleDay.locationDescription) }
    val startTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = startTimeDialogState,
        buttons = {
            positiveButton("확인")
            negativeButton("취소")
        },
    ) {
        timepicker(is24HourClock = true, title = "시작시간") {
            startTime = it.toString()
            startTimeChanged(startTime)
        }
    }
    val endTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = endTimeDialogState,
        buttons = {
            positiveButton("확인")
            negativeButton("취소")
        },
    ) {
        timepicker(
            is24HourClock = true,
            title = "종료시간"
        ) {
            endTime = it.toString()
            endTimeChanged(endTime)
        }
    }
    Row(
        modifier = Modifier
            .background(
                color = White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = "${scheduleDay.dayOfWeek}요일",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.width(18.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = White,
                        shape = RoundedCornerShape(120.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Green,
                        shape = RoundedCornerShape(120.dp)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
            ) {
                Text(
                    text = "영업 시간",
                    fontSize = 12.sp,
                    color = Green,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                )
            }
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = startTime,
                    modifier = Modifier
                        .background(
                            color = Gray5,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 15.dp
                        )
                        .clickable {
                            startTimeDialogState.show()
                        },
                    maxLines = 1,
                )
                Spacer(
                    modifier = Modifier.width(14.dp)
                )
                Text(
                    text = "~",
                    fontSize = 14.sp,
                    color = Black
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = endTime,
                    modifier = Modifier
                        .background(
                            color = Gray5,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 15.dp
                        )
                        .clickable {
                            endTimeDialogState.show()
                        },
                    maxLines = 1,
                )
            }
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = White,
                        shape = RoundedCornerShape(120.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Green,
                        shape = RoundedCornerShape(120.dp)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
            ) {
                Text(
                    text = "출몰 지역",
                    fontSize = 12.sp,
                    color = Green,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                )
            }
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            TextField(
                value = locationDescription,
                placeholder = { Text("영업 장소") },
                modifier = Modifier
                    .background(
                        color = Gray5,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth(),
                onValueChange = {
                    locationDescription = it
                    locationDescriptionChanged(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = Gray30,
                    focusedContainerColor = Gray5,
                    cursorColor = Gray30,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Gray5,
                    focusedSupportingTextColor = Gray100,
                ),
            )
        }
    }
}

@Composable
private fun BusinessScheduleBottom(
    modifier: Modifier = Modifier,
    isEnable: Boolean,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 21.5.dp),
        onClick = { if (isEnable) onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = if (isEnable) Green else Gray30,
        ),
        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "저장하기",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        },
    )
}

internal data class ScheduleDay(
    val dayOfWeek: String,
    val dayOfTheWeek: String,
    val startTime: String,
    val endTime: String,
    val locationDescription: String,
    val isSelected: Boolean,
)
