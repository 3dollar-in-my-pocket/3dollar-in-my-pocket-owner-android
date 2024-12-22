package app.threedollars.manager.feature.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.threedollars.common.ui.Pink_OP20
import app.threedollars.manager.feature.home.R
import app.threedollars.manager.feature.home.model.BossStoreRetrieveAroundVo
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.CircleOverlay
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun MapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    location: LatLng,
    isFoodTruckCheck: Boolean,
    bossStoreArounds: List<BossStoreRetrieveAroundVo>,
) {
    NaverMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(locationTrackingMode = LocationTrackingMode.None),
        uiSettings = MapUiSettings(isZoomControlEnabled = false, isLocationButtonEnabled = false),
    ) {
        Marker(state = MarkerState(location), icon = OverlayImage.fromResource(R.drawable.ic_marker))
        CircleOverlay(center = location, color = Pink_OP20, radius = 148.dp.value.toDouble())
        if (isFoodTruckCheck) {
            bossStoreArounds.forEach {
                Marker(
                    state = MarkerState(LatLng(it.location.latitude, it.location.longitude)),
                    icon = OverlayImage.fromResource(R.drawable.ic_marker_disable)
                )
            }
        }
    }
}