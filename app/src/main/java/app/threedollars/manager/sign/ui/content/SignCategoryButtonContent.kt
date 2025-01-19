package app.threedollars.manager.sign.ui.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.MildGreen
import app.threedollars.manager.feature.storemanagement.model.StoreCategoriesVo

@Composable
fun SignCategoryButtonContent(storeCategoriesVo: StoreCategoriesVo, onClick: () -> Unit) {
    Button(
        modifier = Modifier.wrapContentSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (storeCategoriesVo.isSelected) Color.White else Green,
            containerColor = if (storeCategoriesVo.isSelected) Green else MildGreen
        )
    ) {
        Text(
            text = storeCategoriesVo.name,
            textAlign = TextAlign.Center
        )
    }
}