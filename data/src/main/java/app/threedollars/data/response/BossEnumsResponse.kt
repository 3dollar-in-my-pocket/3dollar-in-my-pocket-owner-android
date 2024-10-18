package app.threedollars.data.response

import app.threedollars.data.BaseResponse
import app.threedollars.data.model.EnumsModel
import app.threedollars.data.model.toDto
import app.threedollars.domain.dto.BossEnumsDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossEnumsResponse(
    @SerialName("PaymentMethodType")
    val paymentMethodType: List<EnumsModel> = listOf(),
    @SerialName("BossStoreOpenType")
    val bossStoreOpenType: List<EnumsModel> = listOf(),
    @SerialName("FeedbackTargetType")
    val feedbackTargetType: List<EnumsModel> = listOf(),
    @SerialName("AdvertisementPositionType")
    val advertisementPositionType: List<EnumsModel> = listOf(),
    @SerialName("UserMenuCategoryType")
    val userMenuCategoryType: List<EnumsModel> = listOf(),
    @SerialName("PushPlatformType")
    val pushPlatformType: List<EnumsModel> = listOf(),
    @SerialName("BossRegistrationStatus")
    val bossRegistrationStatus: List<EnumsModel> = listOf(),
    @SerialName("StoreType")
    val storeType: List<EnumsModel> = listOf(),
    @SerialName("ApplicationType")
    val applicationType: List<EnumsModel> = listOf(),
    @SerialName("DayOfTheWeek")
    val dayOfTheWeek: List<EnumsModel> = listOf(),
    @SerialName("PushOptionsType")
    val pushOptionsType: List<EnumsModel> = listOf(),
    @SerialName("FileType")
    val fileType: List<EnumsModel> = listOf(),
    @SerialName("BossAccountSocialType")
    val bossAccountSocialType: List<EnumsModel> = listOf(),
    @SerialName("BoosRegistrationRejectReasonType")
    val boosRegistrationRejectReasonType: List<EnumsModel> = listOf(),
    @SerialName("DeleteReasonType")
    val deleteReasonType: List<EnumsModel> = listOf(),
    @SerialName("VisitType")
    val visitType: List<EnumsModel> = listOf(),
    @SerialName("FaqCategory")
    val faqCategory: List<EnumsModel> = listOf(),
    @SerialName("StoreSalesType")
    val storeSalesType: List<EnumsModel> = listOf(),
    @SerialName("UserSocialType")
    val userSocialType: List<EnumsModel> = listOf(),
    @SerialName("FeedbackEmojiType")
    val feedbackEmojiType: List<EnumsModel> = listOf(),
    @SerialName("Bank")
    val bankType: List<EnumsModel> = listOf(),
) : BaseResponse<BossEnumsResponse>() {
    fun toDto() = BossEnumsDto(
        paymentMethodType.toDto(),
        bossStoreOpenType.toDto(),
        feedbackTargetType.toDto(),
        advertisementPositionType.toDto(),
        userMenuCategoryType.toDto(),
        pushPlatformType.toDto(),
        bossRegistrationStatus.toDto(),
        storeType.toDto(),
        applicationType.toDto(),
        dayOfTheWeek.toDto(),
        pushOptionsType.toDto(),
        fileType.toDto(),
        bossAccountSocialType.toDto(),
        boosRegistrationRejectReasonType.toDto(),
        deleteReasonType.toDto(),
        visitType.toDto(),
        faqCategory.toDto(),
        storeSalesType.toDto(),
        userSocialType.toDto(),
        feedbackEmojiType.toDto(),
        bankType.toDto(),
    )
}
