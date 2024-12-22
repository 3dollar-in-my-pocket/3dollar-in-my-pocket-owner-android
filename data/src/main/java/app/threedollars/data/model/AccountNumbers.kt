package app.threedollars.data.model

import app.threedollars.domain.dto.AccountNumbersDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountNumbers(
    @SerialName("bank")
    val bank: Bank = Bank(),
    @SerialName("accountHolder")
    val accountHolder: String = "",
    @SerialName("accountNumber")
    val accountNumber: String = "",
    @SerialName("description")
    val description: String? = null,
) {
    fun toDto() = AccountNumbersDto(
        bankDto = bank.toDto(),
        accountHolder = accountHolder,
        accountNumber = accountNumber,
        description = description,
    )
}
