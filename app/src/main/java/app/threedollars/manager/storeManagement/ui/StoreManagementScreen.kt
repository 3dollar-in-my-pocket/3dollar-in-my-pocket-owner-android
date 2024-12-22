package app.threedollars.manager.storeManagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray95

@Composable
fun StoreManagementScreen() {
    var isStoreInfoClicked by remember { mutableStateOf(true) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Gray0)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 24.dp)
        ) {
            TextButton(onClick = { isStoreInfoClicked = true }) {
                Text(
                    text = "가게정보",
                    fontSize = 18.sp,
                    fontWeight = if (isStoreInfoClicked) FontWeight.Bold else null,
                    color = if (isStoreInfoClicked) Gray95 else Gray30
                )
            }
            TextButton(onClick = { isStoreInfoClicked = false }) {
                Text(
                    text = "리뷰통계",
                    fontSize = 18.sp,
                    fontWeight = if (isStoreInfoClicked) null else FontWeight.Bold,
                    color = if (isStoreInfoClicked) Gray30 else Gray95
                )
            }
        }
        if (isStoreInfoClicked) {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            MyScreen()
        } else {
            Spacer(modifier = Modifier.padding(top = 36.dp))
            ReviewScreen()
        }
    }
}