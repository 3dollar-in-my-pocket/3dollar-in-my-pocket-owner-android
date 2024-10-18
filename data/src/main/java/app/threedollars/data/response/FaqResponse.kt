package app.threedollars.data.response


import app.threedollars.common.ext.toStringDefault
import app.threedollars.data.model.CategoryInfoModel
import app.threedollars.domain.dto.CategoryInfoDto
import app.threedollars.domain.dto.FaqDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FaqResponse(
    @SerialName("answer")
    val answer: String? = "",
    @SerialName("category")
    val category: String? = "",
    @SerialName("categoryInfo")
    val categoryInfo: CategoryInfoModel? = CategoryInfoModel(),
    @SerialName("createdAt")
    val createdAt: String? = "",
    @SerialName("faqId")
    val faqId: Int? = 0,
    @SerialName("question")
    val question: String? = "",
    @SerialName("updatedAt")
    val updatedAt: String? = "",
)

fun List<FaqResponse>.toDto() = map {
    FaqDto(
        answer = it.answer.toStringDefault(),
        category = it.category.toStringDefault(),
        categoryInfo = it.categoryInfo?.toDto() ?: CategoryInfoDto(),
        createdAt = it.createdAt.toStringDefault(),
        faqId = it.faqId ?: 0,
        question = it.question.toStringDefault(),
        updatedAt = it.updatedAt.toStringDefault()
    )
}
