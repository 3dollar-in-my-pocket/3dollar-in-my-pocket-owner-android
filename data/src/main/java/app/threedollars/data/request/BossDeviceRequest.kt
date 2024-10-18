package app.threedollars.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossDeviceRequest(
    @SerialName("pushPlatformType")
    val pushPlatformType: String? = null,
    @SerialName("pushToken")
    val pushToken: String? = null,
)