package app.threedollars.data.response


import app.threedollars.data.BaseResponse
import app.threedollars.domain.dto.ImageUploadDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadResponse(
    @SerialName("imageUrl")
    val imageUrl: String? = null,
) : BaseResponse<ImageUploadResponse>() {
    fun toDto() = ImageUploadDto(imageUrl)
}

fun List<ImageUploadResponse>.toDto() = map {
    ImageUploadDto(it.imageUrl)
}

