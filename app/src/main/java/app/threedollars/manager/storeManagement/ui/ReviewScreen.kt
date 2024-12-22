package app.threedollars.manager.storeManagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import app.threedollars.common.ui.*
import app.threedollars.domain.dto.ContentsDto
import app.threedollars.manager.R
import app.threedollars.manager.storeManagement.viewModel.ReviewViewModel
import app.threedollars.manager.util.noRippleClickable
import app.threedollars.manager.vo.FeedbackFullVo
import app.threedollars.manager.vo.FeedbackTypesVo

@Composable
fun ReviewScreen(viewModel: ReviewViewModel = hiltViewModel()) {
    val feedbackFullList = viewModel.feedbackFullList.collectAsState()
    val feedbackTypeList = viewModel.feedbackTypeList.collectAsState()

    val feedbackFullCount = (feedbackFullList.value.sumOf { it.count })
    var isAllDateClicked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
        ReviewHeader(feedbackFullCount)
        AllDateAndSpecificDateButton(isAllDateClicked) { isAllDateClicked = it }

        if (isAllDateClicked) {
            AllDate(feedbackFullList, feedbackTypeList)
        } else {
            SpecificDate(feedbackTypeList, viewModel)
        }
    }
}

@Composable
private fun ReviewHeader(feedbackFullCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = stringResource(R.string.total),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .background(color = if (feedbackFullCount == 0) Gray10 else LightGreen, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp),
            text = stringResource(R.string.units, feedbackFullCount),
            color = if (feedbackFullCount == 0) Gray50 else Green,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(R.string.of),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = stringResource(R.string.review_reached_boss),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun AllDateAndSpecificDateButton(isAllDateClicked: Boolean, onClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp)
            .background(Gray5, RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
                .weight(1f)
                .height(40.dp)
                .background(
                    color = if (isAllDateClicked) Color.White else Gray5,
                    shape = RoundedCornerShape(9.dp)
                )
                .noRippleClickable {
                    onClick(true)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = stringResource(R.string.all),
                color = if (isAllDateClicked) Green else Gray40,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 8.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
                .weight(1f)
                .height(40.dp)
                .background(
                    color = if (isAllDateClicked) Gray5 else Color.White,
                    shape = RoundedCornerShape(9.dp)
                )
                .noRippleClickable {
                    onClick(false)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = stringResource(R.string.by_day),
                color = if (isAllDateClicked) Gray40 else Green,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AllDate(feedbackFullList: State<List<FeedbackFullVo>>, feedbackTypeList: State<List<FeedbackTypesVo>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Color.White)
    ) {

        val sortList = feedbackFullList.value.sortedByDescending { it.count }

        items(count = sortList.size) { index ->
            val feedbackType = feedbackTypeList.value.find {
                it.feedbackType == sortList[index].feedbackType
            }
            if (feedbackType != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                ) {
                    ReviewTextRow(feedbackType, index, sortList[index].count)
                    ReviewProgress(index, sortList)
                }
            }
        }
    }
}

@Composable
private fun ReviewTextRow(
    feedbackType: FeedbackTypesVo,
    index: Int,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "${feedbackType.emoji} ${feedbackType.description}",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .border(1.dp, if (index < 3) Green else Gray30, RoundedCornerShape(32.dp))
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = stringResource(R.string.units, count),
            color = if (index < 3) Green else Gray30
        )
    }
}

@Composable
private fun ReviewProgress(index: Int, sortList: List<FeedbackFullVo>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(top = 10.dp, start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (index < 3) MildGreen else Gray5),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(sortList[index].ratio.toFloat())
                .height(12.dp)
                .padding(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (index < 3) Green else Gray10),
        )
    }
}

@Composable
fun SpecificDate(feedbackTypeList: State<List<FeedbackTypesVo>>, viewModel: ReviewViewModel = hiltViewModel()) {
    val feedbackSpecificDto = viewModel.feedbackSpecificDto.collectAsLazyPagingItems()

    if (feedbackSpecificDto.itemCount != 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp, start = 24.dp, end = 24.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            items(feedbackSpecificDto.itemCount) { index ->
                val data = feedbackSpecificDto[index] ?: ContentsDto()

                Row(modifier = Modifier.fillMaxWidth()) {
                    SpecificDateColumn(index, data)
                    SpecificDateFeedbackColumn(data, feedbackTypeList)
                }
            }
        }
    } else {
        EmptySpecificDate()
    }
}

@Composable
private fun SpecificDateFeedbackColumn(
    data: ContentsDto,
    feedbackTypeList: State<List<FeedbackTypesVo>>
) {
    val sortList = data.feedbacks.sortedByDescending { it.count }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp * sortList.size)
            .padding(start = 12.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {
        items(count = sortList.size) { index ->
            val feedbackType = feedbackTypeList.value.find {
                it.feedbackType == sortList[index].feedbackType
            }
            if (feedbackType != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                ) {
                    ReviewTextRow(feedbackType, index, sortList[index].count)
                }
            }
        }
    }
}

@Composable
private fun SpecificDateColumn(index: Int, data: ContentsDto) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .background(if (index == 0) Green else Color.White, RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.Center
    ) {
        val dateList = data.date.split("-")
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.day_units, dateList[2]),
            color = if (index == 0) Color.White else Green,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.year_month, dateList[0], dateList[1]),
            color = if (index == 0) Color.White else Gray30,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun EmptySpecificDate() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 28.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    )
    {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 24.dp, bottom = 24.dp)
                .align(Alignment.CenterStart),
            text = stringResource(id = R.string.empty_specific_feedback),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )
        Text(
            modifier = Modifier
                .padding(end = 16.dp, top = 24.dp, bottom = 24.dp)
                .align(Alignment.CenterEnd)
                .border(1.dp, Gray30, RoundedCornerShape(32.dp))
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = stringResource(R.string.units, 0),
            color = Gray30
        )
    }
}