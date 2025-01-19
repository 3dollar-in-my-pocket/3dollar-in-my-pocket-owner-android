package app.threedollars.manager.feature.storemanagement

import app.threedollars.manager.feature.storemanagement.components.ScheduleDay
import app.threedollars.manager.feature.storemanagement.model.AppearanceDaysVo
import app.threedollars.manager.feature.storemanagement.model.BankTypeVo
import app.threedollars.manager.feature.storemanagement.model.BossStoreRetrieveVo
import app.threedollars.manager.feature.storemanagement.model.FeedbackFullVo
import app.threedollars.manager.feature.storemanagement.model.FeedbackTypesVo
import app.threedollars.manager.feature.storemanagement.model.StoreCategoriesVo


internal data class StoreManagementState(
    val screenType: ScreenType = ScreenType.STORE_INFO,
    val dialogType: DialogType = DialogType.NONE,
    val errorMessage: String? = null,
    val bossStoreRetrieve: BossStoreRetrieveVo = BossStoreRetrieveVo(),
    val storeCategories: List<StoreCategoriesVo> = listOf(),
    val selectedStoreCategories: List<String> = listOf(),
    val bankTypes: List<BankTypeVo> = listOf(),
    val scheduleDays: List<ScheduleDay> = defaultScheduleDays,
    val appearanceDays: HashMap<String, AppearanceDaysVo> = hashMapOf(),
    val feedbackFulls: List<FeedbackFullVo> = listOf(),
    val feedbackTypes: List<FeedbackTypesVo> = listOf(),
)

internal enum class ScreenType {
    STORE_INFO,
    REVIEW_INFO,
    PROFILE_EDIT,
    BOSS_COMMENT,
    MENU_MANAGEMENT,
    ACCOUNT,
    BUSINESS_SCHEDULE_EDIT
}

internal enum class DialogType {
    NONE,
    ERROR_DIALOG,
    LOADING_DIALOG
}

internal val defaultScheduleDays = listOf(
    ScheduleDay("월", "MONDAY", "", "", "", false),
    ScheduleDay("화", "TUESDAY", "", "", "", false),
    ScheduleDay("수", "WEDNESDAY", "", "", "", false),
    ScheduleDay("목", "THURSDAY", "", "", "", false),
    ScheduleDay("금", "FRIDAY", "", "", "", false),
    ScheduleDay("토", "SATURDAY", "", "", "", false),
    ScheduleDay("일", "SUNDAY", "", "", "", false),
)