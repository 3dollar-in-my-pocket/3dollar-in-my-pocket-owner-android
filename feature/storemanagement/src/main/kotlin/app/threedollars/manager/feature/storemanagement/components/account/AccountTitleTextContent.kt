package app.threedollars.manager.feature.storemanagement.components.account

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ui.Pink

@Composable
internal fun AccountTitleTextContent(
    titleText: String,
    explanationText: String = "",
    isExplanationText: Boolean,
    isRequired: Boolean = true,
) {
    val explanationState by remember { mutableStateOf(isExplanationText) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = 4.dp)
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically),
                text = titleText,
                color = Color.Black,
                fontSize = 14.sp,
            )
            if (isRequired) Canvas(modifier = Modifier.size(4.dp), onDraw = { drawCircle(color = Pink) })
        }

        if (explanationState) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically),
                text = explanationText,
                color = Pink,
                fontSize = 12.sp,
            )
        }
    }
}