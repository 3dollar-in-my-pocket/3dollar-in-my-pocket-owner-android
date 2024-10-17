package app.threedollars.manager.storeManagement.ui.profile

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.threedollars.common.BaseDialog
import app.threedollars.common.ValueWrapper
import app.threedollars.common.ext.toStringDefault
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Green
import app.threedollars.manager.R
import app.threedollars.manager.sign.ui.content.SignCategoryButtonContent
import app.threedollars.manager.sign.ui.content.SignCertificationPhoto
import app.threedollars.manager.sign.ui.content.SignTitleTextContent
import app.threedollars.manager.storeManagement.ui.content.DefaultTextFieldContent
import app.threedollars.manager.storeManagement.viewModel.ProfileEditViewModel
import app.threedollars.manager.util.findActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody

@AndroidEntryPoint
class ProfileEditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProfileEditScreen()
        }
    }

}

@Composable
fun ProfileEditScreen(viewModel: ProfileEditViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val bossStore by viewModel.bossStoreRetrieveMe.collectAsStateWithLifecycle(null)
    val selectedList by viewModel.selectedItems.collectAsStateWithLifecycle()
    val editComplete by viewModel.editComplete.collectAsStateWithLifecycle(initialValue = false)
    var isEnable by remember { mutableStateOf(false) }
    var name by remember(bossStore) { mutableStateOf(bossStore?.name.toStringDefault()) }
    var imageRequestBody by remember { mutableStateOf<RequestBody?>(null) }
    var sns by remember(bossStore) { mutableStateOf(bossStore?.snsUrl.toStringDefault()) }
    val imageUrl by remember(bossStore) { mutableStateOf(bossStore?.imageUrl.toStringDefault()) }
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle(ValueWrapper(""))
    var isErrorDialog by remember { mutableStateOf(false) }
    if (isErrorDialog) {
        BaseDialog(title = "Error", message = errorMessage.value, confirmText = "확인", onConfirm = { isErrorDialog = false })
    }
    LaunchedEffect(errorMessage) {
        if (errorMessage.value.isNotEmpty()) {
            isErrorDialog = true
        }
    }
    isEnable = (name.isNotEmpty() && name != bossStore?.name) ||
            (selectedList.isNotEmpty() && selectedList != bossStore?.categories?.map { it.categoryId }) ||
            imageRequestBody != null ||
            sns != bossStore?.snsUrl
    LaunchedEffect(editComplete) {
        if (editComplete) {
            context.findActivity().setResult(RESULT_OK)
            context.findActivity().finish()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Gray0)
    ) {
        ProfileEditTop()
        ProfileEditContents(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
            viewModel,
            name,
            imageUrl,
            sns,
            onChangeName = { name = it },
            onChangeUri = { imageRequestBody = it },
            onChangeSNS = { sns = it })
        ProfileEditBottom(
            Modifier
                .fillMaxWidth()
                .height(64.dp), isEnable
        ) {
            viewModel.patchBossStore(bossStore?.bossStoreId.toStringDefault(), name, sns, imageRequestBody)
        }
    }
}

@Preview
@Composable
fun ProfileEditTop() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier
            .wrapContentWidth()
            .padding(start = 16.dp), onClick = {
            context.findActivity().finish()
        }) {
            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "")
        }
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = "대표 정보 수정",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun ProfileEditBottom(modifier: Modifier = Modifier, isEnable: Boolean = false, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier.height(64.dp),
        onClick = { if (isEnable) onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = if (isEnable) Green else Gray30
        ),
        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "저장하기",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    )
}


@Composable
fun ProfileEditContents(
    modifier: Modifier = Modifier,
    viewModel: ProfileEditViewModel,
    name: String,
    image: String,
    sns: String,
    onChangeName: (String) -> Unit,
    onChangeUri: (RequestBody) -> Unit,
    onChangeSNS: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        StoreNameTextFieldContent(name, onChangeText = onChangeName)
        SignTitleTextContent(titleText = "카테고리 선택", explanationText = "최대 3개", isExplanationText = true)
        CategoryGrid(viewModel)
        SignTitleTextContent(titleText = "가게 인증 사진", isExplanationText = false)
        SignCertificationPhoto(Uri.parse(image), onChangeUri)
        SignTitleTextContent(titleText = "SNS", isExplanationText = false, isRequired = false)
        DefaultTextFieldContent(sns, "SNS를 입력해 주세요.", maxLength = 50, onChangeText = onChangeSNS)
        Spacer(modifier = Modifier.height(44.dp))
    }
}

@Preview
@Composable
fun StoreNameTextFieldContent(name: String = "", onChangeText: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SignTitleTextContent(titleText = "가게 이름", explanationText = "공백 포함 최대 20자", isExplanationText = true)
        DefaultTextFieldContent(name, "가게 이름을 입력해 주세요.", onChangeText = onChangeText, imeAction = ImeAction.Next)
    }
}

@Composable
fun CategoryGrid(viewModel: ProfileEditViewModel = hiltViewModel()) {
    app.threedollars.common.ui.FlowRow(
        horizontalGap = 8.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        viewModel.categoryItemState.forEachIndexed { index, storeCategoriesVo ->
            SignCategoryButtonContent(storeCategoriesVo) { viewModel.categorySelection(index) }
        }
    }
}


