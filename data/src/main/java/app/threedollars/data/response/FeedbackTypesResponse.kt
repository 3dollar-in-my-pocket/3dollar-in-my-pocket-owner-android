package app.threedollars.data.response


import app.threedollars.data.BaseResponse
import app.threedollars.domain.dto.FeedbackTypesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackTypesResponse(
    @SerialName("description")
    val description: String = "",
    @SerialName("emoji")
    val emoji: String = "",
    @SerialName("feedbackType")
    val feedbackType: String = "",
) : BaseResponse<FeedbackTypesResponse>()

fun List<FeedbackTypesResponse>.toDto() = map {
    FeedbackTypesDto(it.description, it.emoji, it.feedbackType)
}

