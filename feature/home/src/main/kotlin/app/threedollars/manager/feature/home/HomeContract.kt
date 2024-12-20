package app.threedollars.manager.feature.home

import app.threedollars.manager.feature.home.model.BossStoreRetrieveAroundVo
import app.threedollars.manager.feature.home.model.BossStoreRetrieveVo
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap.DEFAULT_CAMERA_POSITION


internal data class HomeState(
    val location: LatLng = DEFAULT_CAMERA_POSITION.target,
    val address: String = "",
    val bossStoreRetrieveMe: BossStoreRetrieveVo = BossStoreRetrieveVo(),
    val bossStoreRetrieveArounds: List<BossStoreRetrieveAroundVo> = listOf(),
)

internal enum class StoreStateType {
    OPEN,
    CLOSE
}