package app.threedollars.manager.feature.home

import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.usecase.BossStoreOpenUseCase
import app.threedollars.domain.usecase.BossStoreRetrieveUseCase
import app.threedollars.manager.feature.home.model.dtoToVo
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val bossStoreRetrieveUseCase: BossStoreRetrieveUseCase,
    private val bossStoreOpenUseCase: BossStoreOpenUseCase,
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())

    val stateFlow: StateFlow<HomeState> = _stateFlow.asStateFlow()

    init {
        getBossStoreRetrieveMe()
    }

    private fun getBossStoreRetrieveMe() {
        viewModelScope.launch(exceptionHandler) {
            bossStoreRetrieveUseCase.getBossStoreRetrieveMe().collect {
                if (it.code.toString() == "200") {
                    it.data?.let { data ->
                        _stateFlow.update { state ->
                            state.copy(
                                bossStoreRetrieveMe = data.dtoToVo()
                            )
                        }
                    }
                }
            }
        }
    }

    fun getBossStoreAround(latLng: LatLng) {
        viewModelScope.launch(exceptionHandler) {
            bossStoreRetrieveUseCase.getBossStoreRetrieveAround(mapLatitude = latLng.latitude, mapLongitude = latLng.longitude).collect {
                if (it.code.toString() == "200") {
                    it.data?.let { data ->
                        _stateFlow.update { state ->
                            state.copy(
                                location = latLng,
                                bossStoreRetrieveArounds = data.map { bossStoreRetrieveAroundDto -> bossStoreRetrieveAroundDto.dtoToVo() }
                            )
                        }
                    }
                }
            }
        }
    }

    fun storeOpen() {
        viewModelScope.launch(exceptionHandler) {
            val bossStoreId = _stateFlow.value.bossStoreRetrieveMe.bossStoreId.toStringDefault()
            val location = _stateFlow.value.location

            bossStoreOpenUseCase.postBossStoreOpen(
                bossStoreId = bossStoreId,
                mapLatitude = location.latitude,
                mapLongitude = location.longitude
            ).collect {
                _stateFlow.update { state ->
                    state.copy(
                        bossStoreRetrieveMe = state.bossStoreRetrieveMe.copy(
                            openStatus = state.bossStoreRetrieveMe.openStatus.copy(
                                status = StoreStateType.OPEN,
                                openStartDateTime = getCurrentTime()
                            )
                        )
                    )
                }
            }
        }
    }

    fun storeClosed() {
        viewModelScope.launch(exceptionHandler) {
            val bossStoreId = _stateFlow.value.bossStoreRetrieveMe.bossStoreId.toStringDefault()

            bossStoreOpenUseCase.deleteBossStoreOpen(
                bossStoreId = bossStoreId
            ).collect {
                _stateFlow.update { state ->
                    state.copy(
                        bossStoreRetrieveMe = state.bossStoreRetrieveMe.copy(
                            openStatus = state.bossStoreRetrieveMe.openStatus.copy(
                                status = StoreStateType.CLOSE
                            )
                        )
                    )
                }
            }
        }
    }

    fun updateAddress(address: String) {
        _stateFlow.update { state ->
            state.copy(
                address = address
            )
        }
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        formatter.format(Date())
        return formatter.format(Date())
    }

}