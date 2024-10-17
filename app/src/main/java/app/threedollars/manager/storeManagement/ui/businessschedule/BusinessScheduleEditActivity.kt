package app.threedollars.manager.storeManagement.ui.businessschedule

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.threedollars.common.ext.toStringDefault
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
import app.threedollars.manager.R
import app.threedollars.manager.storeManagement.viewModel.BusinessScheduleEditViewModel
import app.threedollars.manager.storeManagement.viewModel.defaultScheduleDays
import app.threedollars.manager.util.findActivity
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessScheduleEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessScheduleEditScreen()
        }
    }
}

@Composable
fun BusinessScheduleEditScreen(viewModel: BusinessScheduleEditViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val bossStore by viewModel.bossStoreRetrieveMe.collectAsStateWithLifecycle(null)
    val editComplete by viewModel.editComplete.collectAsStateWithLifecycle(initialValue = false)
    val isEnable by viewModel.enableComplete.collectAsStateWithLifecycle(initialValue = false)
    LaunchedEffect(editComplete) {
        if (editComplete) {
            context.findActivity().setResult(RESULT_OK)
            context.findActivity().finish()
        }
    }
    bossStore?.appearanceDays?.forEach {
        it.dayOfTheWeek
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Gray0),
    ) {
        BusinessScheduleEditTop()
        BusinessScheduleContents(
            Modifier
                .fillMaxSize()
                .weight(1f),
        )
        BusinessScheduleBottom(
            Modifier
                .fillMaxWidth()
                .height(64.dp),
            isEnable,
        ) {
            viewModel.patchBossStore(bossStore?.bossStoreId.toStringDefault())
        }
    }
}

@Preview
@Composable
fun BusinessScheduleEditTop() {
    val context = LocalContext.current
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
                context.findActivity().finish()
            },
        ) {
            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "")
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

@Preview
@Composable
fun BusinessScheduleContents(modifier: Modifier = Modifier, viewModel: BusinessScheduleEditViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val scheduleDays by viewModel.scheduleDays.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        BusinessScheduleSelectDay()
        Spacer(modifier = Modifier.height(48.dp))
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            scheduleDays.forEach {
                if (it.isSelected) {
                    BusinessScheduleDayDetail(it, { startTime ->
                        viewModel.editDaysStartTime(it.dayOfTheWeek, startTime)
                    }, { endTime ->
                        viewModel.editDaysEndTime(it.dayOfTheWeek, endTime)
                    }, { locationDescription ->
                        viewModel.editDaysLocationDescription(it.dayOfTheWeek, locationDescription)
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Preview
@Composable
fun BusinessScheduleSelectDay() {
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
        BusinessScheduleDays()
    }
}

@Preview
@Composable
fun BusinessScheduleDays(viewModel: BusinessScheduleEditViewModel = hiltViewModel()) {
    val scheduleDays by viewModel.scheduleDays.collectAsStateWithLifecycle()
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        scheduleDays.forEach {
            BusinessScheduleDay(it) { editSchedule ->
                viewModel.selectedScheduleDays(editSchedule)
            }
        }
    }
}

@Preview
@Composable
fun BusinessScheduleDay(scheduleDay: ScheduleDay = defaultScheduleDays[0], clickDay: (ScheduleDay) -> Unit = {}) {
    val isSelected = scheduleDay.isSelected
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Black else Gray5)
            .border(width = if (isSelected) 0.dp else 1.dp, color = if (isSelected) Black else Gray10, shape = CircleShape)
            .clickable {
                val day = if (isSelected) {
                    scheduleDay.copy(startTime = "", endTime = "", locationDescription = "", isSelected = false)
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

@Preview
@Composable
fun BusinessScheduleDayDetail(
    scheduleDay: ScheduleDay = defaultScheduleDays[0],
    startTimeChanged: (String) -> Unit = {},
    endTimeChanged: (String) -> Unit = {},
    locationDescriptionChanged: (String) -> Unit = {},
) {
    var startTime by remember(scheduleDay) { mutableStateOf(scheduleDay.startTime.ifEmpty { "시작시간" }) }
    var endTime by remember(scheduleDay) { mutableStateOf(scheduleDay.endTime.ifEmpty { "종료시간" }) }
    var locationDescription by remember(scheduleDay) { mutableStateOf(scheduleDay.locationDescription) }
    val startTimeDialogState = rememberMaterialDialogState()
    Log.e("aaaaaaaa", scheduleDay.toString())
    Log.e("startTime", startTime)
    Log.e("endTime", endTime)
    Log.e("locationDescription", locationDescription)
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
        timepicker(is24HourClock = true, title = "종료시간") {
            endTime = it.toString()
            endTimeChanged(endTime)
        }
    }
    Row(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text(text = "${scheduleDay.dayOfWeek}요일", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(18.dp))
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .background(color = White, shape = RoundedCornerShape(120.dp))
                    .border(1.dp, color = Green, shape = RoundedCornerShape(120.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "영업 시간",
                    fontSize = 12.sp,
                    color = Green,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = startTime,
                    modifier = Modifier
                        .background(color = Gray5, shape = RoundedCornerShape(8.dp))
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 15.dp)
                        .clickable {
                            startTimeDialogState.show()
                        },
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(text = "~", fontSize = 14.sp, color = Black)
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = endTime,
                    modifier = Modifier
                        .background(color = Gray5, shape = RoundedCornerShape(8.dp))
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 15.dp)
                        .clickable {
                            endTimeDialogState.show()
                        },
                    maxLines = 1,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .background(color = White, shape = RoundedCornerShape(120.dp))
                    .border(1.dp, color = Green, shape = RoundedCornerShape(120.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "출몰 지역",
                    fontSize = 12.sp,
                    color = Green,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = locationDescription,
                placeholder = { Text("영업 장소") },
                modifier = Modifier
                    .background(color = Gray5, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                onValueChange = {
                    locationDescription = it
                    locationDescriptionChanged(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    placeholderColor = Gray30,
                    backgroundColor = Gray5,
                    cursorColor = Gray30,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Gray100,
                ),
            )
        }
    }
}

@Preview
@Composable
fun BusinessScheduleBottom(modifier: Modifier = Modifier, isEnable: Boolean = false, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 21.5.dp),
        onClick = { if (isEnable) onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = if (isEnable) Green else Gray30,
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

data class ScheduleDay(
    val dayOfWeek: String,
    val dayOfTheWeek: String,
    val startTime: String,
    val endTime: String,
    val locationDescription: String,
    val isSelected: Boolean,
)
