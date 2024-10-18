package app.threedollars.manager.feature.setting.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.threedollars.manager.feature.setting.R

@Composable
fun SettingCategoryContent(
    modifier: Modifier = Modifier,
    leftImage: Int? = null,
    leftText: String,
    rightText: String? = null,
    rightImage: Int? = null,
    rightTextColor: Int = R.color.white,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.gray90))
            .padding(vertical = 20.dp, horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        if (leftImage != null) {
            Image(
                painter = painterResource(id = leftImage), contentDescription = null
            )
        }

        Text(
            text = leftText,
            color = Color.White,
            modifier = Modifier.padding(start = if (leftImage != null) 8.dp else 0.dp)
        )

        if (rightText != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = rightText,
                color = colorResource(id = rightTextColor),
                textAlign = TextAlign.End
            )
        }

        if (rightImage != null) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.CenterEnd,
                painter = painterResource(id = rightImage),
                contentDescription = null
            )
        }
    }
}
