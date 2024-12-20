package app.threedollars.manager.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
internal fun HomeBottomOff(modifier: Modifier, click: () -> Unit) {
    val scroll = rememberScrollState()
    Column(
        modifier
            .background(Color.White, shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
            .verticalScroll(scroll)
    ) {
        Text(
            text = "지금 계신 위치에서 영업을 시작할까요?",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 2.dp)
        )
        Text(text = "영업을 시작하면 위치가 손님들에게 공개됩니다.", style = TextStyle(fontSize = 14.sp), modifier = Modifier.padding(start = 2.dp, top = 2.dp))
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "오늘의 영업 시작하기",
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, color = Color.White),
            modifier = Modifier
                .clickable { click() }
                .fillMaxWidth()
                .height(48.dp)
                .background(colorResource(id = R.color.green500), shape = RoundedCornerShape(8.dp))
                .wrapContentHeight(Alignment.CenterVertically),
        )
    }
}
