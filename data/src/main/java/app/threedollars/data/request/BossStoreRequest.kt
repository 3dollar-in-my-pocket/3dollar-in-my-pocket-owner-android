package app.threedollars.data.request


import app.threedollars.data.model.AppearanceDaysRequestModel
import app.threedollars.data.model.MenusModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossStoreRequest(
    @SerialName("appearanceDays")
    val appearanceDays: List<AppearanceDaysRequestModel>? = null,
    @SerialName("categoriesIds")
    val categoriesIds: List<String>? = null,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("introduction")
    val introduction: String? = null,
    @SerialName("menus")
    val menus: List<MenusModel>? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("snsUrl")
    val snsUrl: String? = null,
    @SerialName("accountNumbers")
    val accountNumbers: List<AccountNumberRequest>? = null,
)