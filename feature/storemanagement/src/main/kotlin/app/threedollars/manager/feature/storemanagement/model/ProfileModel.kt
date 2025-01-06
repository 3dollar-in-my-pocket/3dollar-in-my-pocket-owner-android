package app.threedollars.manager.feature.storemanagement.model

data class ProfileModel(
    val image: String,
    val name: String,
    val category: List<String>,
    val snsLink: String,
)
