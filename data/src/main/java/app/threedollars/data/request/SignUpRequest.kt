package app.threedollars.data.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("bossName")
    val bossName: String? = null,
    @SerialName("businessNumber")
    val businessNumber: String? = null,
    @SerialName("certificationPhotoUrl")
    val certificationPhotoUrl: String? = null,
    @SerialName("socialType")
    val socialType: String? = null,
    @SerialName("storeCategoriesIds")
    val storeCategoriesIds: List<String>? = null,
    @SerialName("storeName")
    val storeName: String? = null,
    @SerialName("token")
    val token: String? = null,
)