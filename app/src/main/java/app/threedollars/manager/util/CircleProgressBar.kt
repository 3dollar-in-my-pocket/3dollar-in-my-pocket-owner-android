package app.threedollars.manager.util

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.threedollars.common.ui.Green

@Composable
fun CircleProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(50.dp),
        color = Green,
        strokeWidth = 5.dp
    )
}