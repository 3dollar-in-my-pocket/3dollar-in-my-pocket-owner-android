package app.threedollars.manager.feature.setting

import app.threedollars.domain.dto.BossAccountInfoDto
import app.threedollars.domain.dto.FaqDto

/**
 * UI State that represents SettingScreen
 **/
data class SettingState(
    val bossAccountInfo: BossAccountInfoDto = BossAccountInfoDto(),
    val faqList: List<FaqDto> = listOf(),
    val pageType: PageType = PageType.SETTING,
    val isSuccess: Boolean = false,
)

enum class PageType {
    SETTING, FAQ
}