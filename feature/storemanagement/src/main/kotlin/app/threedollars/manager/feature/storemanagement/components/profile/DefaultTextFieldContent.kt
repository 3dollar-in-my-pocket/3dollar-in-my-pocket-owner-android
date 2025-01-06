package app.threedollars.manager.feature.storemanagement.components.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray5

@Preview
@Composable
internal fun DefaultTextFieldContent(
    default: String = "",
    hint: String = "가게 이름을 입력해주세요.",
    maxLength: Int = 20,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onChangeText: (String) -> Unit = {},
) {

    TextField(
        value = default,
        onValueChange = { newText ->
            if (newText.length <= maxLength) {
                onChangeText(newText)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 24.dp, end = 24.dp),
        placeholder = { Text(hint) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Gray30,
            focusedContainerColor = Gray5,
            cursorColor = Gray30,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Gray5,
            disabledIndicatorColor = Color.Transparent
        )
    )
}