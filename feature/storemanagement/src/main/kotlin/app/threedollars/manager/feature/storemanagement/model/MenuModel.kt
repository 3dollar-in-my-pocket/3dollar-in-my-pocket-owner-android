package app.threedollars.manager.feature.storemanagement.model

import okhttp3.RequestBody

internal data class MenuModel(
    var imageRequestBody: RequestBody? = null,
    var imageUrl: String? = null,
    var name: String? = null,
    var price: Int? = null,
    var isEmpty: Boolean = false,
)