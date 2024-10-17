package app.threedollars.manager.sign.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.threedollars.common.BaseDialog
import app.threedollars.common.ValueWrapper
import app.threedollars.common.ext.getResourceUri
import app.threedollars.common.ui.FlowRow
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Green
import app.threedollars.manager.R
import app.threedollars.manager.sign.LoginNavItem
import app.threedollars.manager.sign.ui.content.SignCategoryButtonContent
import app.threedollars.manager.sign.ui.content.SignCertificationPhoto
import app.threedollars.manager.sign.ui.content.SignTextFieldContent
import app.threedollars.manager.sign.ui.content.SignTitleTextContent
import app.threedollars.manager.sign.viewmodel.SignViewModel
import okhttp3.RequestBody

@Composable
fun SignScreen(navController: NavHostController, viewModel: SignViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val navItem by viewModel.loginNavItem.collectAsStateWithLifecycle(null)
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle(ValueWrapper(""))
    var isErrorDialog by remember { mutableStateOf(false) }

    if (isErrorDialog) {
        BaseDialog(title = "Error", message = errorMessage.value, confirmText = "확인", onConfirm = { isErrorDialog = false })
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Gray5)
            .verticalScroll(scrollState)
    ) {
        SignTop(navController)
        SignBottom(viewModel)
    }
    LaunchedEffect(navItem) {
        navItem?.let {
            navController.popBackStack()
            navController.navigate(it.screenRoute)
        }
    }
    LaunchedEffect(errorMessage) {
        if (errorMessage.value.isNotEmpty()) {
            isErrorDialog = true
        }
    }
}

@Composable
fun SignTop(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier
            .wrapContentWidth()
            .padding(start = 16.dp), onClick = {
            navController.popBackStack()
            navController.navigate(LoginNavItem.Login.screenRoute)
        }) {
            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "")
        }
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = "회원가입",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 60.dp),
        text = "사장님,가게 정보 등록하시고\n부자 되세요~!",
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )
}

@Composable
private fun SignBottom(viewModel: SignViewModel) {
    var bossName by remember { mutableStateOf("") }
    var storeName by remember { mutableStateOf("") }
    var businessNumber by remember { mutableStateOf("") }
    var certificationPhotoUri by remember { mutableStateOf<RequestBody?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 24.dp)
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InitTextField(
            onChangeBossNameText = { bossName = it },
            onChangeStoreNameText = { storeName = it },
            onChangeBusinessNumberText = { businessNumber = it }
        )
        InitCategoryGrid(viewModel)
        InitCertificationPhoto(onChangeUri = { certificationPhotoUri = it })
        InitSignUpButton {
            viewModel.signUp(bossName, storeName, businessNumber, certificationPhotoUri)
        }
    }
}


@Composable
private fun InitTextField(
    onChangeBossNameText: (String) -> Unit,
    onChangeStoreNameText: (String) -> Unit,
    onChangeBusinessNumberText: (String) -> Unit,
) {
    SignTextFieldContent(
        titleText = "사장님 성함",
        hintText = "사장님 성함을 입력해주세요.",
        isExplanationText = false,
        keyboardType = KeyboardType.Text,
        onChangeText = {
            onChangeBossNameText(it)
        },
        imeAction = ImeAction.Next
    )
    SignTextFieldContent(
        titleText = "가게 이름",
        hintText = "가게 이름을 입력해 주세요.",
        explanationText = "공백 포함 최대 20자",
        isExplanationText = true,
        keyboardType = KeyboardType.Text,
        onChangeText = {
            onChangeStoreNameText(it)
        },
        imeAction = ImeAction.Next
    )
    SignTextFieldContent(
        titleText = "사업자 등록 번호",
        hintText = "사업자 등록 번호를 입력해 주세요.",
        explanationText = "\"-\"를 제외한 숫자만 입력",
        isExplanationText = true,
        keyboardType = KeyboardType.Number,
        onChangeText = {
            onChangeBusinessNumberText(it)
        },
        imeAction = ImeAction.Done
    )
}


@Composable
private fun InitCategoryGrid(viewModel: SignViewModel) {
    SignTitleTextContent(titleText = "카테고리 선택", explanationText = "최대 3개", isExplanationText = true)
    CategoryGrid(viewModel)
}

@Composable
fun CategoryGrid(viewModel: SignViewModel) {
    FlowRow(
        horizontalGap = 8.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        viewModel.categoryItemState.forEachIndexed { index, storeCategoriesVo ->
            SignCategoryButtonContent(storeCategoriesVo) { viewModel.categorySelection(index) }
        }
    }
}


@Composable
fun InitCertificationPhoto(onChangeUri: (RequestBody) -> Unit) {
    val context = LocalContext.current
    SignTitleTextContent(titleText = "가게 인증 사진", isExplanationText = false)
    SignCertificationPhoto(R.drawable.ic_certification_photo.getResourceUri(context), onChangeUri)
}

@Composable
fun InitSignUpButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Green
        )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "가입 신청하기",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}