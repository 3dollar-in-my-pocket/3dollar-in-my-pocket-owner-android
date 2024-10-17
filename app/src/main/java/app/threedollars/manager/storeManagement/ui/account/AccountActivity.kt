package app.threedollars.manager.storeManagement.ui.account

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.threedollars.common.ext.toStringDefault
import app.threedollars.common.ui.*
import app.threedollars.manager.R
import app.threedollars.manager.sign.ui.content.SignTitleTextContent
import app.threedollars.manager.storeManagement.ui.content.DefaultTextFieldContent
import app.threedollars.manager.storeManagement.viewModel.AccountViewModel
import app.threedollars.manager.util.findActivity
import app.threedollars.manager.vo.BankVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddAccountScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun AddAccountScreen(viewModel: AccountViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val bossStore by viewModel.bossStoreRetrieveMe.collectAsStateWithLifecycle(null)
    val bankEnums by viewModel.bankEnum.collectAsStateWithLifecycle(listOf())
    var accountNumber by remember(bossStore) { mutableStateOf(bossStore?.accountNumbers?.firstOrNull()?.accountNumber.toStringDefault()) }
    var accountHolder by remember(bossStore) { mutableStateOf(bossStore?.accountNumbers?.firstOrNull()?.accountHolder.toStringDefault()) }
    var accountBank by remember(bossStore) { mutableStateOf(bossStore?.accountNumbers?.firstOrNull()?.bankVo) }
    val editComplete by viewModel.editComplete.collectAsStateWithLifecycle(initialValue = false)
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(editComplete) {
        if (editComplete) {
            Toast.makeText(context, "저장 완료!", Toast.LENGTH_SHORT).show()
            context.findActivity().setResult(Activity.RESULT_OK)
            context.findActivity().finish()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Gray0),
    ) {
        ModalBottomSheetLayout(
            sheetContent = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "은행 선택",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Black
                            )
                        )
                        Image(
                            modifier = Modifier.clickable { coroutineScope.launch { sheetState.hide() } },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_24),
                            contentDescription = null
                        )
                    }
                    LazyVerticalGrid(
                        modifier = Modifier.defaultMinSize(minHeight = 100.dp),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(bankEnums) { bank ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (accountBank?.key == bank.key) Green else Gray40,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        accountBank = BankVo(key = bank.key.toStringDefault(), description = bank.description.toStringDefault())
                                    }
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    text = bank.description.toString(),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = if (accountBank?.key == bank.key) Black else Gray50
                                    )
                                )
                                if (accountBank?.key == bank.key) {
                                    Image(
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .align(Alignment.CenterEnd),
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_green_20),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            },
            sheetShape = RoundedCornerShape(16.dp),
            sheetBackgroundColor = White,
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    TopContent()
                    BodyContent(
                        accountNumber = accountNumber,
                        accountHolder = accountHolder,
                        accountBankName = accountBank?.description,
                        bottomSheetState = sheetState,
                        onChangeAccountNumber = { accountNumber = it },
                        onChangeAccountHolder = { accountHolder = it },
                    )
                }
                BottomContent(
                    isEnable = (accountBank != null) && (accountHolder.isNotEmpty()) && (accountNumber.isNotEmpty()),
                    onClick = {
                        viewModel.patchBossStore(
                            id = bossStore?.bossStoreId.toStringDefault(),
                            accountNumber = accountNumber,
                            accountHolder = accountHolder,
                            accountBank = accountBank?.key.toStringDefault()
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TopContent() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 16.dp),
            onClick = {
                context.findActivity().finish()
            }) {
            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "")
        }
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = "계좌정보 등록",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BodyContent(
    accountNumber: String,
    accountHolder: String,
    accountBankName: String?,
    bottomSheetState: ModalBottomSheetState,
    onChangeAccountNumber: (String) -> Unit,
    onChangeAccountHolder: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AccountNumberItem(accountNumber, onChangeAccountNumber)
        BankItem(accountBankName, bottomSheetState)
        AccountHolderItem(accountHolder, onChangeAccountHolder)
    }
}

@Composable
private fun AccountHolderItem(accountHolder: String, onChangeAccountHolder: (String) -> Unit) {
    SignTitleTextContent(
        titleText = "예금주",
        isExplanationText = false
    )
    DefaultTextFieldContent(
        default = accountHolder,
        hint = "예금주를 입력해주세요.",
        keyboardType = KeyboardType.Text,
        onChangeText = onChangeAccountHolder,
        imeAction = ImeAction.Next
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BankItem(
    accountBankName: String? = null, bottomSheetState: ModalBottomSheetState,
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    SignTitleTextContent(
        titleText = "은행",
        isExplanationText = false
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 24.dp, end = 24.dp)
            .background(Gray5, shape = RoundedCornerShape(8.dp))
            .clickable {
                focusManager.clearFocus()
                coroutineScope.launch { bottomSheetState.show() }
            },
    ) {
        Text(
            text = accountBankName ?: "은행을 선택해주세요.",
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp, bottom = 20.dp, start = 16.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = if (accountBankName == null) Gray30 else Gray100
            ),
        )
    }
}

@Composable
private fun AccountNumberItem(accountNumber: String, onChangeAccountNumber: (String) -> Unit) {
    SignTitleTextContent(
        titleText = "계좌번호",
        isExplanationText = false
    )
    DefaultTextFieldContent(
        default = accountNumber,
        hint = "계좌번호를 입력해주세요.",
        keyboardType = KeyboardType.Number,
        onChangeText = onChangeAccountNumber,
        imeAction = ImeAction.Done
    )
}


@Preview
@Composable
fun BottomContent(modifier: Modifier = Modifier, isEnable: Boolean = false, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 21.5.dp),
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