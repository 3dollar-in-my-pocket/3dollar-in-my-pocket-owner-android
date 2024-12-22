package app.threedollars.manager.sign.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignTextFieldContent(
    titleText: String,
    hintText: String,
    onChangeText: (String) -> Unit,
    explanationText: String = "",
    isExplanationText: Boolean,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SignTitleTextContent(titleText = titleText, explanationText = explanationText, isExplanationText = isExplanationText)

        TextField(
            value = text,
            onValueChange = { newText ->
                if (newText.text.length <= 20) {
                    onChangeText(newText.text)
                    text = newText
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 24.dp, end = 24.dp),
            placeholder = { Text(hintText) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = Gray30,
                focusedContainerColor = Gray5,
                cursorColor = Gray30,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
            )
        )
    }
}