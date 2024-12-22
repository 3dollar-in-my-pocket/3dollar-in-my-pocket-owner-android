package app.threedollars.data.response

import app.threedollars.data.BaseResponse
import app.threedollars.data.model.AccountNumbers
import app.threedollars.data.model.Address
import app.threedollars.data.model.AppearanceDaysModel
import app.threedollars.data.model.CategoriesModel
import app.threedollars.data.model.LocationModel
import app.threedollars.data.model.MenusModel
import app.threedollars.data.model.OpenStatusModel
import app.threedollars.data.model.toDto
import app.threedollars.domain.dto.AddressDto
import app.threedollars.domain.dto.BossStoreRetrieveDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossStoreRetrieveResponse(
    @SerialName("bossStoreId")
    val bossStoreId: String? = null,
    @SerialName("isOwner")
    val isOwner: Boolean? = true,
    @SerialName("name")
    val name: String? = null,
    @SerialName("location")
    val location: LocationModel? = null,
    @SerialName("address")
    val address: Address? = Address(),
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("introduction")
    val introduction: String? = null,
    @SerialName("snsUrl")
    val snsUrl: String? = null,
    @SerialName("menus")
    val menus: List<MenusModel>?,
    @SerialName("appearanceDays")
    val appearanceDays: List<AppearanceDaysModel>? = listOf(),
    @SerialName("categories")
    val categories: List<CategoriesModel>? = listOf(),
    @SerialName("accountNumbers")
    val accountNumbers: List<AccountNumbers>? = listOf(),
    @SerialName("openStatus")
    val openStatus: OpenStatusModel? = null,
    @SerialName("distance")
    val distance: Int? = null,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
) : BaseResponse<BossStoreRetrieveResponse>() {
    fun toDto() = BossStoreRetrieveDto(
        bossStoreId = bossStoreId,
        isOwner = isOwner ?: true,
        name = name,
        location = location?.toDto(),
        address = address?.toDto() ?: AddressDto(),
        imageUrl = imageUrl,
        introduction = introduction,
        snsUrl = snsUrl,
        menus = menus?.toDto() ?: listOf(),
        appearanceDays = appearanceDays?.toDto() ?: listOf(),
        categories = categories?.toDto() ?: listOf(),
        accountNumbersDto = accountNumbers?.map { it.toDto() } ?: listOf(),
        createdAt = createdAt,
        distance = distance,
        openStatus = openStatus?.toDto(),
        updatedAt = updatedAt,
    )
}
