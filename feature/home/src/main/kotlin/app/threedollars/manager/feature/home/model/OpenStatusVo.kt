package app.threedollars.manager.feature.home.model

import app.threedollars.manager.feature.home.StoreStateType


internal data class OpenStatusVo(
    val openStartDateTime: String? = null,
    val status: StoreStateType = StoreStateType.CLOSE,
)