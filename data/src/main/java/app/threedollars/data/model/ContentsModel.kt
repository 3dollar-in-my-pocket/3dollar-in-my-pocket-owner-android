package app.threedollars.data.model


import app.threedollars.domain.dto.ContentsDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentsModel(
    @SerialName("date")
    val date: String = "",
    @SerialName("feedbacks")
    val feedbacks: List<FeedbacksModel> = listOf(),
)

fun ContentsModel.toDto() = ContentsDto(date, feedbacks.toDto())
