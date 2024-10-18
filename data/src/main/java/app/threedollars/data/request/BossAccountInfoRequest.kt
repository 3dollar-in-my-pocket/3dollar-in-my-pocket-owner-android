package app.threedollars.data.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossAccountInfoRequest(
    @SerialName("name")
    val name: String? = null,
)