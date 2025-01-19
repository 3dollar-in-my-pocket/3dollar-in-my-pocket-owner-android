package app.threedollars.manager.feature.storemanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import app.threedollars.common.BaseDialog
import app.threedollars.common.ui.Gray0
import app.threedollars.common.ui.Gray30
import app.threedollars.common.ui.Gray95
import app.threedollars.domain.dto.ContentsDto
import app.threedollars.manager.feature.storemanagement.components.BossCommentScreen
import app.threedollars.manager.feature.storemanagement.components.BusinessScheduleEditScreen
import app.threedollars.manager.feature.storemanagement.components.MyScreen
import app.threedollars.manager.feature.storemanagement.components.ReviewScreen
import app.threedollars.manager.feature.storemanagement.components.ScheduleDay
import app.threedollars.manager.feature.storemanagement.components.account.AccountScreen
import app.threedollars.manager.feature.storemanagement.components.menumanagement.MenuManagementScreen
import app.threedollars.manager.feature.storemanagement.components.profile.ProfileEditScreen
import app.threedollars.manager.feature.storemanagement.model.AppearanceDaysVo
import app.threedollars.manager.feature.storemanagement.model.BankTypeVo
import app.threedollars.manager.feature.storemanagement.model.BossStorePatchModel
import app.threedollars.manager.feature.storemanagement.model.BossStoreRetrieveVo
import app.threedollars.manager.feature.storemanagement.model.FeedbackFullVo
import app.threedollars.manager.feature.storemanagement.model.FeedbackTypesVo
import app.threedollars.manager.feature.storemanagement.model.StoreCategoriesVo

@Composable
internal fun StoreManagementScreen(
    screenType: ScreenType,
    dialogType: DialogType,
    bossStoreRetrieve: BossStoreRetrieveVo,
    storeCategories: List<StoreCategoriesVo>,
    selectedStoreCategories: List<String>,
    bankTypes: List<BankTypeVo>,
    errorMessage: String?,
    scheduleDays: List<ScheduleDay>,
    appearanceDays: HashMap<String, AppearanceDaysVo>,
    feedbackFulls: List<FeedbackFullVo>,
    feedbackTypes: List<FeedbackTypesVo>,
    feedbackSpecific: LazyPagingItems<ContentsDto>,
    onScreenTypeUpdate: (ScreenType) -> Unit,
    onDialogTypeUpdate: (DialogType) -> Unit,
    onBossStorePatch: (BossStorePatchModel) -> Unit,
    onMenuPatch: (BossStorePatchModel) -> Unit,
    onStoreCategorySelected: (Int) -> Unit,
    onStartTimeUpdate: (String, String) -> Unit,
    onEndTimeUpdate: (String, String) -> Unit,
    onLocationDescriptionUpdate: (String, String) -> Unit,
    onScheduleDayUpdate: (ScheduleDay) -> Unit,
) {
    if (dialogType == DialogType.ERROR_DIALOG) {
        BaseDialog(
            title = "Error",
            message = errorMessage.toString(),
            confirmText = "확인",
            onConfirm = { onDialogTypeUpdate(DialogType.NONE) }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (screenType == ScreenType.STORE_INFO || screenType == ScreenType.REVIEW_INFO) {
                TopBar(
                    screenType = screenType,
                    onScreenTypeUpdate = onScreenTypeUpdate
                )
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(innerPadding)
        ) {
            when (screenType) {
                ScreenType.STORE_INFO -> {
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    MyScreen(
                        bossStoreRetrieve = bossStoreRetrieve,
                        onScreenTypeUpdate = onScreenTypeUpdate
                    )
                }

                ScreenType.REVIEW_INFO -> {
                    Spacer(modifier = Modifier.padding(top = 36.dp))
                    ReviewScreen(
                        feedbackFulls = feedbackFulls,
                        feedbackTypes = feedbackTypes,
                        feedbackSpecific = feedbackSpecific
                    )
                }

                ScreenType.PROFILE_EDIT -> {
                    ProfileEditScreen(
                        bossStoreRetrieve = bossStoreRetrieve,
                        storeCategories = storeCategories,
                        selectedStoreCategories = selectedStoreCategories,
                        onScreenTypeUpdate = onScreenTypeUpdate,
                        onBossStorePatch = onBossStorePatch,
                        onStoreCategorySelected = onStoreCategorySelected
                    )
                }

                ScreenType.BOSS_COMMENT -> {
                    BossCommentScreen(
                        bossStoreRetrieve = bossStoreRetrieve,
                        onScreenTypeUpdate = onScreenTypeUpdate,
                        onBossStorePatch = onBossStorePatch
                    )
                }

                ScreenType.MENU_MANAGEMENT -> {
                    MenuManagementScreen(
                        bossStoreRetrieve = bossStoreRetrieve,
                        dialogType = dialogType,
                        errorMessage = errorMessage,
                        onMenuPatch = onMenuPatch,
                        onScreenTypeUpdate = onScreenTypeUpdate
                    )
                }

                ScreenType.ACCOUNT -> {
                    AccountScreen(
                        bossStoreRetrieve = bossStoreRetrieve,
                        bankTypes = bankTypes,
                        onScreenTypeUpdate = onScreenTypeUpdate,
                        onBossStorePatch = onBossStorePatch
                    )
                }

                ScreenType.BUSINESS_SCHEDULE_EDIT -> {
                    BusinessScheduleEditScreen(
                        bossStoreRetrieve = bossStoreRetrieve,
                        scheduleDays = scheduleDays,
                        appearanceDays = appearanceDays,
                        onScreenTypeUpdate = onScreenTypeUpdate,
                        onBossStorePatch = onBossStorePatch,
                        onStartTimeUpdate = onStartTimeUpdate,
                        onEndTimeUpdate = onEndTimeUpdate,
                        onLocationDescriptionUpdate = onLocationDescriptionUpdate,
                        onScheduleDayUpdate = onScheduleDayUpdate,
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onScreenTypeUpdate: (ScreenType) -> Unit,
    screenType: ScreenType,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray0)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    top = 24.dp
                )
        ) {
            TextButton(onClick = { onScreenTypeUpdate(ScreenType.STORE_INFO) }) {
                Text(
                    text = "가게정보",
                    fontSize = 18.sp,
                    fontWeight = if (screenType == ScreenType.STORE_INFO) FontWeight.Bold else null,
                    color = if (screenType == ScreenType.STORE_INFO) Gray95 else Gray30
                )
            }
            TextButton(onClick = { onScreenTypeUpdate(ScreenType.REVIEW_INFO) }) {
                Text(
                    text = "리뷰통계",
                    fontSize = 18.sp,
                    fontWeight = if (screenType == ScreenType.REVIEW_INFO) FontWeight.Bold else null,
                    color = if (screenType == ScreenType.REVIEW_INFO) Gray95 else Gray30
                )
            }
        }
    }
}