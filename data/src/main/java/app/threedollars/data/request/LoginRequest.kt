package app.threedollars.data.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("socialType")
    val socialType: String? = null,
    @SerialName("token")
    val token: String? = null,
)