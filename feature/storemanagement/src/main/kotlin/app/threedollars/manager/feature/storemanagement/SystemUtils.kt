package app.threedollars.manager.feature.storemanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

internal fun Int.toWon(): String {
    val formatter = java.text.DecimalFormat("#,###")
    return "${formatter.format(this)}원"
}

internal fun convertImageUrlToRequestBody(imageUrl: String): RequestBody? {
    return try {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(imageUrl)
            .build()
        val response = client.newCall(request).execute()
        val body = response.body?.bytes()
        body?.toRequestBody("image/*".toMediaTypeOrNull())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

internal inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
