package app.threedollars.manager.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.threedollars.common.ui.Gray95
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.GreenOp50
import app.threedollars.manager.R
import app.threedollars.manager.sign.viewmodel.SettingViewModel
import app.threedollars.manager.vo.FaqVo

@Composable
fun FaqScreen(
    navController: NavHostController, viewModel: SettingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .padding(24.dp)
    ) {
        viewModel.getFaq()
        val faq by viewModel.faq.collectAsStateWithLifecycle(listOf())

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.popBackStack()
                    }, painter = painterResource(id = R.drawable.ic_back_white), contentDescription = ""
            )
            Text(
                modifier = Modifier.align(Alignment.Center), text = stringResource(R.string.faq), fontSize = 16.sp, color = Color.White
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 60.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.faq_title),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.W500
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            val categorySet = mutableSetOf<String>()

            items(faq.size) {
                val category = faq[it].categoryInfo.category

                if (!categorySet.contains(category)) {
                    FaqCategoryContent(faq[it].categoryInfo.description, categorySet, category)
                }
                Spacer(modifier = Modifier.padding(top = 16.dp))
                FaqContent(faq[it])
            }
        }
    }
}

@Composable
private fun FaqContent(faq: FaqVo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Gray95)
            .padding(start = 16.dp, end = 20.dp, bottom = 16.dp)
    ) {
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Text(text = stringResource(R.string.q), fontSize = 14.sp, color = Green, fontWeight = FontWeight.W700)
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = faq.question, fontSize = 14.sp, color = Green, fontWeight = FontWeight.W700
            )
        }
        Text(
            modifier = Modifier.padding(start = 22.dp, top = 12.dp),
            text = faq.answer,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
private fun FaqCategoryContent(
    text: String,
    categorySet: MutableSet<String>,
    category: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .drawBehind {
                    val borderSize = 8.dp.toPx()
                    val y = size.height - borderSize / 2
                    drawLine(
                        color = GreenOp50,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderSize
                    )
                },
            text = text,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.W700
        )
        categorySet.add(category)
    }
}
