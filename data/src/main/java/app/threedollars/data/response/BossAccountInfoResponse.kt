package app.threedollars.data.response


import app.threedollars.common.ext.toStringDefault
import app.threedollars.data.BaseResponse
import app.threedollars.domain.dto.BossAccountInfoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossAccountInfoResponse(
    @SerialName("bossId")
    val bossId: String? = "",
    @SerialName("businessNumber")
    val businessNumber: String? = "",
    @SerialName("createdAt")
    val createdAt: String? = "",
    @SerialName("isSetupNotification")
    val isSetupNotification: Boolean? = false,
    @SerialName("name")
    val name: String? = "",
    @SerialName("socialType")
    val socialType: String? = "",
    @SerialName("updatedAt")
    val updatedAt: String? = "",
) : BaseResponse<BossAccountInfoResponse>() {
    fun toDto() = BossAccountInfoDto(
        bossId = bossId.toStringDefault(),
        businessNumber = businessNumber.toStringDefault(),
        createdAt = createdAt.toStringDefault(),
        isSetupNotification = isSetupNotification ?: false,
        name = name.toStringDefault(),
        socialType = socialType.toStringDefault(),
        updatedAt = updatedAt.toStringDefault()
    )
}