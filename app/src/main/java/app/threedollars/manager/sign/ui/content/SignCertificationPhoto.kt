package app.threedollars.manager.sign.ui.content

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Gray50
import app.threedollars.common.ui.Green
import app.threedollars.manager.R
import app.threedollars.manager.util.ContentUriToRequestBody
import coil.compose.AsyncImage
import coil.request.ImageRequest
import okhttp3.RequestBody

@Composable
fun SignCertificationPhoto(defaultImage: Uri, onChangeUri: (RequestBody) -> Unit) {
    val context = LocalContext.current
    var selectImage by remember(defaultImage) {
        mutableStateOf(defaultImage)
    }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectImage = uri
                onChangeUri(ContentUriToRequestBody(context, uri))
            }
        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 48.dp)
    ) {
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = "사장님 가게를 대표하는 사진을 등록해주세요!\n가게 인증을 통해 홍보를 도와드릴게요!",
            color = Gray50,
            fontSize = 14.sp,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(Gray5, shape = RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(selectImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_certification_photo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(top = 14.dp, start = 12.dp, end = 12.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = {
                    galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Green,
                    containerColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "이미지 업로드",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}