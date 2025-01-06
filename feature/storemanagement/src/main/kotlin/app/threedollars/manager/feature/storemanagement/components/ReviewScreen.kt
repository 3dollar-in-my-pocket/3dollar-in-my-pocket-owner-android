package app.threedollars.manager.feature.storemanagement.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import app.threedollars.common.ui.Gray10
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray40
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Gray50
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.LightGreen
import app.threedollars.common.ui.MildGreen
import app.threedollars.domain.dto.ContentsDto
import app.threedollars.manager.feature.storemanagement.model.FeedbackFullVo
import app.threedollars.manager.feature.storemanagement.model.FeedbackTypesVo
import app.threedollars.manager.feature.storemanagement.noRippleClickable

@Composable
internal fun ReviewScreen(
    feedbackFulls: List<FeedbackFullVo>,
    feedbackTypes: List<FeedbackTypesVo>,
    feedbackSpecific: LazyPagingItems<ContentsDto>,
) {
    val feedbackFullCount = (feedbackFulls.sumOf { it.count })
    var isAllDateClicked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ReviewHeader(
            feedbackFullCount = feedbackFullCount
        )
        AllDateAndSpecificDateButton(
            isAllDateClicked = isAllDateClicked,
            onClick = { isAllDateClicked = it }
        )

        if (isAllDateClicked) {
            AllDate(
                feedbackFulls = feedbackFulls,
                feedbackTypes = feedbackTypes
            )
        } else {
            SpecificDate(
                feedbackTypes = feedbackTypes,
                feedbackSpecific = feedbackSpecific
            )
        }
    }
}

@Composable
private fun ReviewHeader(
    feedbackFullCount: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = "총",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .background(
                    color = if (feedbackFullCount == 0) {
                        Gray10
                    } else {
                        LightGreen
                    },
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp),
            text = "${feedbackFullCount}개",
            color = if (feedbackFullCount == 0) Gray50 else Green,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "의",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = "리뷰가 사장님께 도착했어요 :)",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun AllDateAndSpecificDateButton(
    isAllDateClicked: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 24.dp,
                end = 24.dp
            )
            .background(
                color = Gray5,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 4.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
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
                text = "전체",
                color = if (isAllDateClicked) Green else Gray40,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 4.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
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
                text = "일별",
                color = if (isAllDateClicked) Gray40 else Green,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AllDate(
    feedbackFulls: List<FeedbackFullVo>,
    feedbackTypes: List<FeedbackTypesVo>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .background(Color.White)
    ) {

        val sortList = feedbackFulls.sortedByDescending { it.count }

        items(count = sortList.size) { index ->
            val feedbackType = feedbackTypes.find {
                it.feedbackType == sortList[index].feedbackType
            }
            if (feedbackType != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                ) {
                    ReviewTextRow(
                        feedbackType = feedbackType,
                        index = index,
                        count = sortList[index].count
                    )
                    ReviewProgress(
                        index = index,
                        sortList = sortList
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewTextRow(
    feedbackType: FeedbackTypesVo,
    index: Int,
    count: Int,
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
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (index < 3) Green else Gray30,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                ),
            text = "${count}개",
            color = if (index < 3) Green else Gray30
        )
    }
}

@Composable
private fun ReviewProgress(
    index: Int,
    sortList: List<FeedbackFullVo>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(
                top = 10.dp,
                start = 24.dp,
                end = 24.dp
            )
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
private fun SpecificDate(
    feedbackTypes: List<FeedbackTypesVo>,
    feedbackSpecific: LazyPagingItems<ContentsDto>,
) {
    if (feedbackSpecific.itemCount != 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 28.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 64.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            items(feedbackSpecific.itemCount) { index ->
                val data = feedbackSpecific[index] ?: ContentsDto()

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SpecificDateColumn(
                        index = index,
                        data = data
                    )
                    SpecificDateFeedbackColumn(
                        data = data,
                        feedbackTypes = feedbackTypes
                    )
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
    feedbackTypes: List<FeedbackTypesVo>,
) {
    val sortList = data.feedbacks.sortedByDescending { it.count }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp * sortList.size)
            .padding(start = 12.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        items(count = sortList.size) { index ->
            val feedbackType = feedbackTypes.find {
                it.feedbackType == sortList[index].feedbackType
            }
            if (feedbackType != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                ) {
                    ReviewTextRow(
                        feedbackType = feedbackType,
                        index = index,
                        count = sortList[index].count
                    )
                }
            }
        }
    }
}

@Composable
private fun SpecificDateColumn(
    index: Int,
    data: ContentsDto,
) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .background(
                color = if (index == 0) Green else Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Center
    ) {
        val dateList = data.date.split("-")
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            text = "${dateList[2]}일",
            color = if (index == 0) Color.White else Green,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            text = "${dateList[0]}.${dateList[1]}",
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
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 28.dp
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    )
    {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 24.dp,
                    bottom = 24.dp
                )
                .align(Alignment.CenterStart),
            text = "\uD83E\uDD72 아직 정보가 없어요",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )
        Text(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 24.dp
                )
                .align(Alignment.CenterEnd)
                .border(
                    width = 1.dp,
                    color = Gray30,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                ),
            text = "0개",
            color = Gray30
        )
    }
}