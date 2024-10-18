package app.threedollars.data.response


import app.threedollars.data.BaseResponse
import app.threedollars.data.model.CategoriesModel
import app.threedollars.data.model.LocationModel
import app.threedollars.data.model.MenusModel
import app.threedollars.data.model.OpenStatusModel
import app.threedollars.data.model.toDto
import app.threedollars.domain.dto.BossStoreRetrieveAroundDto
import app.threedollars.domain.dto.OpenStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BossStoreRetrieveAroundResponse(
    @SerialName("bossStoreId")
    val bossStoreId: String? = null,
    @SerialName("categories")
    val categories: List<CategoriesModel>? = listOf(),
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("distance")
    val distance: Int? = null,
    @SerialName("location")
    val location: LocationModel? = null,
    @SerialName("menus")
    val menus: List<MenusModel>? = listOf(),
    @SerialName("name")
    val name: String? = null,
    @SerialName("openStatus")
    val openStatus: OpenStatusModel?,
    @SerialName("totalFeedbacksCounts")
    val totalFeedbacksCounts: Int? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
) : BaseResponse<BossStoreRetrieveAroundResponse>()

fun List<BossStoreRetrieveAroundResponse>.toDto() = map {
    BossStoreRetrieveAroundDto(
        bossStoreId = it.bossStoreId,
        categories = it.categories?.toDto() ?: listOf(),
        createdAt = it.createdAt,
        distance = it.distance,
        location = it.location?.toDto(),
        menus = it.menus?.toDto() ?: listOf(),
        name = it.name,
        openStatus = it.openStatus?.toDto() ?: OpenStatusDto(),
        totalFeedbacksCounts = it.totalFeedbacksCounts,
        updatedAt = it.updatedAt
    )
}
