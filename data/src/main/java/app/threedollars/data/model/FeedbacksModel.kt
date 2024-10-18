package app.threedollars.data.model


import app.threedollars.domain.dto.FeedbacksDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbacksModel(
    @SerialName("count")
    val count: Int = 0,
    @SerialName("feedbackType")
    val feedbackType: String = "",
)

fun List<FeedbacksModel>.toDto() = map {
    FeedbacksDto(it.count, it.feedbackType)
}
