package app.threedollars.data.response


import app.threedollars.data.BaseResponse
import app.threedollars.data.model.ContentsModel
import app.threedollars.data.model.CursorModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackSpecificResponse(
    @SerialName("contents")
    val contents: List<ContentsModel> = listOf(),
    @SerialName("cursor")
    val cursor: CursorModel = CursorModel(),
) : BaseResponse<FeedbackSpecificResponse>()
