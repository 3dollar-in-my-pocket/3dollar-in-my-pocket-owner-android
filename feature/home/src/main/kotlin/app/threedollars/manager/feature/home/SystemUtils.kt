package app.threedollars.manager.feature.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.geometry.LatLng
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun Context.getCurrentLocationName(location: LatLng?): String {
    val notFindMsg = "알 수 없는 위치"
    if (location == null) {
        return notFindMsg
    }

    val geoCoder = Geocoder(this, Locale.KOREA)
    return try {
        val addresses = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
        if (addresses.isNullOrEmpty()) {
            notFindMsg
        } else {
            with(addresses[0]) {
                val locality = subLocality ?: locality ?: ""
                val region = adminArea ?: ""
                if (region.isEmpty() && locality.isEmpty()) {
                    return notFindMsg
                }
                "$region $locality"
            }
        }
    } catch (e: Exception) {
        Log.e("getCurrentLocationName", e.message ?: "")
        notFindMsg
    }
}

internal fun currentLocationState(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onCurrentLocation: (LatLng) -> Unit
) {
    val permissionCheck =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    if (permissionCheck) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                if (location.latitude in -90.0..90.0 && location.longitude in -180.0..180.0) {
                    onCurrentLocation(LatLng(location.latitude, location.longitude))
                } else {
                    Toast.makeText(context, "잘못된 위치 값입니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "위치정보가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Log.e("currentLocationState", e.message ?: "위치 가져오기 실패")
            Toast.makeText(context, "위치를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
    }
}

internal fun String.getTodayOpenTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val openTime = formatter.parse(this)
    val currentTime = Date()
    val durationInMillis = currentTime.time - (openTime?.time ?: 0)
    val hours = durationInMillis / (60 * 60 * 1000)
    val minutes = durationInMillis % (60 * 60 * 1000) / (60 * 1000)
    val seconds = durationInMillis % (60 * 1000) / 1000
    return String.format("%d시 %02d분 %02d초", hours, minutes, seconds)
}