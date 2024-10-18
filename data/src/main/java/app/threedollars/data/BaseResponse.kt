package app.threedollars.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String? = "",
    @SerialName("resultCode")
    val resultCode: String? = ""
)