package app.threedollars.data.response


import app.threedollars.common.ext.toStringDefault
import app.threedollars.data.BaseResponse
import app.threedollars.domain.dto.FeedbackFullDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackFullResponse(
    @SerialName("count")
    val count: Int? = 0,
    @SerialName("feedbackType")
    val feedbackType: String? = "",
    @SerialName("ratio")
    val ratio: String? = "",
) : BaseResponse<FeedbackFullResponse>()

fun List<FeedbackFullResponse>.toDto() = map {
    FeedbackFullDto(
        count = it.count ?: 0,
        feedbackType = it.feedbackType.toStringDefault(),
        ratio = it.ratio.toStringDefault()
    )
}

