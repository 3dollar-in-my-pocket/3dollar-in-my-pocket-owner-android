package app.threedollars.data.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountNumberRequest(
    @SerialName("accountNumber")
    val accountNumber: String,
    @SerialName("accountHolder")
    val accountHolder: String,
    @SerialName("bank")
    val bank: String,
)