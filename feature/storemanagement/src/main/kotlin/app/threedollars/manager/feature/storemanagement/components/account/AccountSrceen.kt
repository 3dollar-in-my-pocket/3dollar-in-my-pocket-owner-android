package app.threedollars.manager.feature.storemanagement.components.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.threedollars.common.ext.toStringDefault
import app.threedollars.common.ui.Black
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray100
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray40
import app.threedollars.common.ui.Gray5
import app.threedollars.common.ui.Gray50
import app.threedollars.common.ui.Green
import app.threedollars.common.ui.White
import app.threedollars.manager.feature.storemanagement.R
import app.threedollars.manager.feature.storemanagement.ScreenType
import app.threedollars.manager.feature.storemanagement.components.profile.DefaultTextFieldContent
import app.threedollars.manager.feature.storemanagement.model.BankTypeVo
import app.threedollars.manager.feature.storemanagement.model.BankVo
import app.threedollars.manager.feature.storemanagement.model.BossStorePatchModel
import app.threedollars.manager.feature.storemanagement.model.BossStoreRetrieveVo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountScreen(
    bossStoreRetrieve: BossStoreRetrieveVo,
    bankTypes: List<BankTypeVo>,
    onScreenTypeUpdate: (ScreenType) -> Unit,
    onBossStorePatch: (BossStorePatchModel) -> Unit,
) {
    var accountNumber by remember { mutableStateOf(bossStoreRetrieve.accountNumbers.firstOrNull()?.accountNumber.toStringDefault()) }
    var accountHolder by remember { mutableStateOf(bossStoreRetrieve.accountNumbers.firstOrNull()?.accountHolder.toStringDefault()) }
    var accountBank by remember { mutableStateOf(bossStoreRetrieve.accountNumbers.firstOrNull()?.bankVo) }
    var isShowDialog by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Gray0),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                TopContent(
                    onScreenTypeUpdate = onScreenTypeUpdate
                )
                BodyContent(
                    accountNumber = accountNumber,
                    accountHolder = accountHolder,
                    accountBankName = accountBank?.description,
                    bottomSheetState = sheetState,
                    onChangeAccountNumber = { accountNumber = it },
                    onChangeAccountHolder = { accountHolder = it },
                    onIsShowDialogUpdate = { isShowDialog = it }
                )
            }
            BottomContent(
                isEnable = (accountBank != null) && (accountHolder.isNotEmpty()) && (accountNumber.isNotEmpty()),
                onClick = {
                    onBossStorePatch(
                        BossStorePatchModel(
                            bossStoreId = bossStoreRetrieve.bossStoreId,
                            accountNumber = accountNumber,
                            accountHolder = accountHolder,
                            accountBank = accountBank?.key
                        )
                    )
                }
            )
        }
        if (isShowDialog) {
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                        isShowDialog = false
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = White,
                sheetState = sheetState,
            ) {
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
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    sheetState.hide()
                                    isShowDialog = false
                                }
                            },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_24),
                            contentDescription = null
                        )
                    }
                    LazyVerticalGrid(
                        modifier = Modifier.defaultMinSize(minHeight = 100.dp),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(bankTypes) { bank ->
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
                                        accountBank = BankVo(
                                            key = bank.key.toStringDefault(),
                                            description = bank.description.toStringDefault()
                                        )
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
            }
        }
    }
}

@Composable
private fun TopContent(
    onScreenTypeUpdate: (ScreenType) -> Unit,
) {
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
                onScreenTypeUpdate(ScreenType.STORE_INFO)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BodyContent(
    accountNumber: String,
    accountHolder: String,
    accountBankName: String?,
    bottomSheetState: SheetState,
    onChangeAccountNumber: (String) -> Unit,
    onChangeAccountHolder: (String) -> Unit,
    onIsShowDialogUpdate: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AccountNumberItem(
            accountNumber = accountNumber,
            onChangeAccountNumber = onChangeAccountNumber
        )
        BankItem(
            accountBankName = accountBankName,
            bottomSheetState = bottomSheetState,
            onIsShowDialogUpdate = onIsShowDialogUpdate
        )
        AccountHolderItem(
            accountHolder = accountHolder,
            onChangeAccountHolder = onChangeAccountHolder
        )
    }
}

@Composable
private fun AccountHolderItem(accountHolder: String, onChangeAccountHolder: (String) -> Unit) {
    AccountTitleTextContent(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BankItem(
    accountBankName: String? = null,
    bottomSheetState: SheetState,
    onIsShowDialogUpdate: (Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    AccountTitleTextContent(
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
                coroutineScope.launch {
                    onIsShowDialogUpdate(true)
                    bottomSheetState.show()
                }
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
    AccountTitleTextContent(
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


@Composable
fun BottomContent(modifier: Modifier = Modifier, isEnable: Boolean = false, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 21.5.dp),
        onClick = { if (isEnable) onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = if (isEnable) Green else Gray30
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