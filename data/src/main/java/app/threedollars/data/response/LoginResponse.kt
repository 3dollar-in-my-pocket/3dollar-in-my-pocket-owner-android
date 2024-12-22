package app.threedollars.data.response


import app.threedollars.domain.dto.LoginDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("bossId")
    val bossId: String? = null,
    @SerialName("token")
    val token: String? = null,
) {
    fun toDto() = LoginDto(bossId, token)

}