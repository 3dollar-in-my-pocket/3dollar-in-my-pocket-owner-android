package app.threedollars.manager.feature.storemanagement.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Gray50
import app.threedollars.common.ui.Green
import app.threedollars.manager.feature.storemanagement.R
import app.threedollars.manager.feature.storemanagement.ScreenType
import app.threedollars.manager.feature.storemanagement.model.BossStorePatchModel
import app.threedollars.manager.feature.storemanagement.model.BossStoreRetrieveVo

@Composable
internal fun BossCommentScreen(
    bossStoreRetrieve: BossStoreRetrieveVo,
    onScreenTypeUpdate: (ScreenType) -> Unit,
    onBossStorePatch: (BossStorePatchModel) -> Unit,
) {
    var introduction by remember { mutableStateOf(bossStoreRetrieve.introduction) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp
                )
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        onScreenTypeUpdate(ScreenType.STORE_INFO)
                    },
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "사장님 한마디 수정",
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.W700)) {
                    append("손님들에게 하고 싶은 말")
                }
                append("을\n적어주세요!")
            }, modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 52.dp
            ),
            fontWeight = FontWeight.W400,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 12.dp
            ),
            text = "ex) 오전에 오시면 서비스가 있습니다 \uD83D\uDE0B\n",
            fontSize = 14.sp,
            color = Gray50,
            fontWeight = FontWeight.W400
        )

        TextField(
            value = introduction,
            onValueChange = { newText ->
                introduction = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(274.dp)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp
                ),
            placeholder = { Text("사장님 한마디를 입력해 주세요!") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = Gray30,
                focusedContainerColor = Gray5,
                cursorColor = Gray30,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = Color.White
            ),
            onClick = {
                onBossStorePatch(
                    BossStorePatchModel(
                        bossStoreId = bossStoreRetrieve.bossStoreId,
                        introduction = introduction
                    )
                )
            }) {
            Text(
                text = "저장하기",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        }
    }
}