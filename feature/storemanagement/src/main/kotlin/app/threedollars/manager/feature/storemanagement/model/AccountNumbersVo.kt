package app.threedollars.manager.feature.storemanagement.model

internal data class AccountNumbersVo(
    val bankVo: BankVo = BankVo(),
    val accountHolder: String = "",
    val accountNumber: String = "",
    val description: String? = null,
)
