package app.threedollars.manager.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.manager.feature.home.R
import app.threedollars.manager.feature.home.getTodayOpenTime
import kotlinx.coroutines.delay

@Composable
fun HomeBottomOn(modifier: Modifier, openDate: String, click: () -> Unit) {
    var openTime by remember { mutableStateOf("") }
    val scroll = rememberScrollState()
    LaunchedEffect(Unit) {
        while (true) {
            openTime = openDate.getTodayOpenTime()
            delay(1000L)
        }
    }
    Column(
        modifier
            .background(colorResource(id = R.color.green500), shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
            .verticalScroll(scroll)
    ) {
        Row(modifier = Modifier.padding(start = 2.dp)) {
            Text(
                text = "오늘은",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = openTime,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White),
                modifier = Modifier
                    .background(colorResource(id = R.color.green300), shape = RoundedCornerShape(8.dp))
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }

        Text(
            text = "동안 영업중이시네요! 오늘도 대박나세요!",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.padding(start = 2.dp, top = 2.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "셔터 내리기",
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, color = colorResource(id = R.color.green500)),
            modifier = Modifier
                .clickable { click() }
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .wrapContentHeight(Alignment.CenterVertically),
        )
    }
}