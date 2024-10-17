package app.threedollars.manager.storeManagement.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import app.threedollars.common.ext.getResourceUri
import app.threedollars.common.ext.toast
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Gray70
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.Red
import app.threedollars.manager.R
import app.threedollars.manager.storeManagement.data.MenuModel
import app.threedollars.manager.storeManagement.viewModel.MyViewModel
import app.threedollars.manager.util.CircleProgressBar
import app.threedollars.manager.util.ContentUriToRequestBody
import app.threedollars.manager.util.convertImageUrlToRequestBody
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody
import java.text.DecimalFormat

@AndroidEntryPoint
class MenuManagementActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuManagementScreen()
        }
    }

    @Composable
    fun MenuManagementScreen(viewModel: MyViewModel = hiltViewModel()) {
        viewModel.getBossStoreRetrieveMe()
        val bossStore = viewModel.bossStoreRetrieveMe.collectAsState(null)
        val isSuccess = viewModel.isSuccess.collectAsState(null)
        val context = LocalContext.current
        var menuList by remember { mutableStateOf(listOf(MenuModel())) }
        var isLoading by remember { mutableStateOf(true) }
        var menuSize by remember { mutableIntStateOf(0) }

        LaunchedEffect(isSuccess.value) {
            isLoading = false
            if (isSuccess.value == true) {
                setResult(RESULT_OK)
                finish()
                context.toast("수정 완료")
            } else {
                menuList = menuList.map {
                    val boolean = it.name.isNullOrEmpty() || it.price == null || it.imageRequestBody == null
                    it.copy(isEmpty = boolean)
                }
            }
        }
        LaunchedEffect(bossStore.value) {
            menuList = bossStore.value?.menus?.map {
                val requestBody = convertImageUrlToRequestBody(it.imageUrl.toString())
                MenuModel(
                    imageUrl = it.imageUrl,
                    name = it.name,
                    price = it.price,
                    imageRequestBody = requestBody,
                )
            }?.toMutableList() ?: mutableListOf()
            menuSize = bossStore.value?.menus?.size ?: 0
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray0),
        ) {
            var isClickDeleteButton by remember { mutableStateOf(false) }
            var isAllDeleteClicked by remember { mutableStateOf(false) }

            TopBar()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 60.dp, bottom = 16.dp),
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Gray70)) {
                            append("$menuSize/20개")
                        }
                        append("의 메뉴가 등록되어 있습니다.")
                    },
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = Gray30,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            if (isClickDeleteButton) {
                                isAllDeleteClicked = true
                            } else {
                                isClickDeleteButton = true
                            }
                        },
                    text = if (isClickDeleteButton) "전체 삭제" else "삭제",
                    fontSize = 14.sp,
                    color = Red,
                    fontWeight = FontWeight.W700,
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 24.dp),
                ) {
                    Box {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(count = menuList.size) { index ->
                                var menuName by remember { mutableStateOf(TextFieldValue(menuList[index].name ?: "")) }
                                var price by remember { mutableStateOf(TextFieldValue(if (menuList[index].price == null) "" else menuList[index].price.toString())) }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .width(if (isClickDeleteButton) 303.dp else 327.dp)
                                            .wrapContentHeight()
                                            .padding(start = 24.dp, top = 16.dp, bottom = 8.dp)
                                            .background(Color.White, RoundedCornerShape(16.dp))
                                            .border(
                                                width = 1.dp,
                                                color = if (menuList[index].isEmpty) Red else Color.White,
                                                shape = RoundedCornerShape(16.dp),
                                            )
                                            .padding(12.dp),
                                    ) {
                                        MenuPhoto(
                                            modifier = Modifier
                                                .padding(bottom = if (menuList[index].isEmpty) 24.dp else 0.dp)
                                                .align(Alignment.TopStart),
                                            defaultImage = if (menuList[index].imageUrl.isNullOrEmpty()) {
                                                R.drawable.ic_menu_default.getResourceUri(
                                                    context,
                                                )
                                            } else {
                                                menuList[index].imageUrl.toString().toUri()
                                            },
                                        ) {
                                            menuList[index].imageRequestBody = it
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(if (isClickDeleteButton) 0.5f else 0.6f)
                                                .padding(start = 12.dp, bottom = if (menuList[index].isEmpty) 24.dp else 0.dp)
                                                .align(Alignment.TopEnd),
                                        ) {
                                            TextField(
                                                value = menuName,
                                                onValueChange = { newText ->
                                                    if (newText.text.length <= 10) {
                                                        menuName = newText
                                                        menuList[index].name = newText.text.ifEmpty { null } ?: ""
                                                    }
                                                },
                                                singleLine = true,
                                                modifier = Modifier.fillMaxWidth(),
                                                placeholder = { Text("메뉴를 입력해 주세요", fontSize = 14.sp, fontWeight = FontWeight.W400) },
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                                                shape = RoundedCornerShape(8.dp),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    placeholderColor = Gray30,
                                                    backgroundColor = Gray5,
                                                    cursorColor = Gray30,
                                                    disabledTextColor = Color.Transparent,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent,
                                                    disabledIndicatorColor = Color.Transparent,
                                                ),
                                                textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400),
                                            )
                                            TextField(
                                                value = price,
                                                onValueChange = { newText ->
                                                    if (newText.text.length <= 10) {
                                                        val newPrice: String = newText.text.replace(",", "").replace("원", "")
                                                        val formatter = DecimalFormat("###,###")
                                                        val result =
                                                            if (newPrice.isEmpty()) {
                                                                newPrice
                                                            } else {
                                                                "${formatter.format(newPrice.toInt())}원"
                                                            }
                                                        price = newText.copy(result)
                                                        menuList[index].price = if (newPrice.isEmpty()) null else newPrice.toInt()
                                                    }
                                                },
                                                singleLine = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 8.dp),
                                                placeholder = { Text("가격을 입력해 주세요", fontSize = 14.sp, fontWeight = FontWeight.W400) },
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                                shape = RoundedCornerShape(8.dp),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    placeholderColor = Gray30,
                                                    backgroundColor = Gray5,
                                                    cursorColor = Gray30,
                                                    disabledTextColor = Color.Transparent,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent,
                                                    disabledIndicatorColor = Color.Transparent,
                                                ),
                                                textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400),
                                            )
                                        }
                                        if (menuList[index].isEmpty) {
                                            Text(
                                                modifier = Modifier
                                                    .padding(top = 16.dp)
                                                    .align(Alignment.BottomStart),
                                                text = "* 메뉴명, 가격, 사진을 모두 등록해주세요.",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.W400,
                                                color = Red,
                                            )
                                        }
                                    }
                                    if (isClickDeleteButton) {
                                        Image(
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .clickable {
                                                    menuList = menuList
                                                        .filterIndexed { i, _ -> index != i }
                                                        .toMutableList()
                                                    menuSize--
                                                },
                                            painter = painterResource(id = R.drawable.ic_delete_circle),
                                            contentDescription = "",
                                        )
                                    }
                                }
                            }
                            if (menuList.size < 20) {
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                                            .border(1.dp, SolidColor(Green), RoundedCornerShape(8.dp))
                                            .clickable {
                                                menuList = (menuList + mutableListOf(MenuModel())) as MutableList<MenuModel>
                                                menuSize++
                                            },
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Image(
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            painter = painterResource(id = R.drawable.ic_plus_circle),
                                            contentDescription = "",
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp, top = 14.dp, bottom = 14.dp),
                                            text = "메뉴 추가하기",
                                            fontWeight = FontWeight.W700,
                                            fontSize = 14.sp,
                                            color = Green,
                                        )
                                    }
                                }
                            }
                        }
                        if (isLoading) {
                            CircleProgressBar(modifier = Modifier.align(Alignment.Center))
                        }
                        if (isAllDeleteClicked) {
                            Spacer(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(color = Color.Gray.copy(alpha = .1f)),
                            )
                            DeleteDialog(modifier = Modifier.align(Alignment.Center), onCancelListener = {
                                isAllDeleteClicked = false
                            }, onAgreeListener = {
                                isClickDeleteButton = false
                                isAllDeleteClicked = false
                                menuList = listOf()
                                menuSize = 0
                            })
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green, contentColor = Color.White),
                    onClick = {
                        if (isClickDeleteButton) {
                            isClickDeleteButton = false
                        } else {
                            viewModel.patchMenu(bossStoreId = bossStore.value?.bossStoreId, fileType = "BOSS_STORE_MENU_IMAGE", menuList)
                            isLoading = true
                        }
                    },
                ) {
                    Text(text = if (isClickDeleteButton) "삭제 완료" else "저장하기", fontSize = 16.sp, fontWeight = FontWeight.W500)
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        finish()
                    },
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "메뉴 관리",
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }

    @Composable
    fun MenuPhoto(modifier: Modifier, defaultImage: Uri, onChangeUri: (RequestBody) -> Unit) {
        val context = LocalContext.current
        var selectImage by remember {
            mutableStateOf(defaultImage)
        }
        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    selectImage = uri
                    onChangeUri(ContentUriToRequestBody(context, uri))
                }
            }
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(selectImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_menu_default),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .width(118.dp)
                .height(118.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .clickable {
                    galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
        )
    }

    @Composable
    fun DeleteDialog(modifier: Modifier = Modifier, onCancelListener: () -> Unit, onAgreeListener: () -> Unit) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
        ) {
            Column {
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(),
                )

                Text(
                    text = "전체 메뉴를 삭제하시겠습니까?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(vertical = 8.dp),
                    fontSize = 14.sp,
                )
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(),
                )
                Row() {
                    Button(
                        onClick = { onCancelListener() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                            .align(Alignment.CenterVertically),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Gray70,
                        ),
                    ) {
                        Text(text = "취소", fontSize = 14.sp)
                    }
                    Button(
                        onClick = { onAgreeListener() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                            .align(Alignment.CenterVertically),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Green,
                        ),
                    ) {
                        Text(text = "삭제", fontSize = 14.sp)
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}
