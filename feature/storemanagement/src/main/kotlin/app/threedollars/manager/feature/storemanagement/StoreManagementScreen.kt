package app.threedollars.manager.feature.storemanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray95
import app.threedollars.manager.feature.storemanagement.components.MyScreen
import app.threedollars.manager.feature.storemanagement.components.ReviewScreen

@Composable
internal fun StoreManagementScreen(
    screenType: ScreenType,
    onScreenTypeUpdate: (ScreenType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray0)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 24.dp)
        ) {
            TextButton(onClick = { onScreenTypeUpdate(ScreenType.STORE_INFO) }) {
                Text(
                    text = "가게정보",
                    fontSize = 18.sp,
                    fontWeight = if (screenType == ScreenType.STORE_INFO) FontWeight.Bold else null,
                    color = if (screenType == ScreenType.STORE_INFO) Gray95 else Gray30
                )
            }
            TextButton(onClick = { onScreenTypeUpdate(ScreenType.REVIEW_INFO) }) {
                Text(
                    text = "리뷰통계",
                    fontSize = 18.sp,
                    fontWeight = if (screenType == ScreenType.REVIEW_INFO) null else FontWeight.Bold,
                    color = if (screenType == ScreenType.REVIEW_INFO) Gray30 else Gray95
                )
            }
        }
        when (screenType) {
            ScreenType.STORE_INFO -> {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                MyScreen()
            }

            ScreenType.REVIEW_INFO -> {
                Spacer(modifier = Modifier.padding(top = 36.dp))
                ReviewScreen()
            }
        }
    }
}

@Composable
@Preview(name = "StoreManagement")
private fun StoreManagementScreenPreview() {
    StoreManagementScreen(
        screenType = ScreenType.STORE_INFO,
        onScreenTypeUpdate = {}
    )
}
