package app.threedollars.manager.feature.storemanagement

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class StoreManagementViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow: MutableStateFlow<StoreManagementState> = MutableStateFlow(StoreManagementState())

    val stateFlow: StateFlow<StoreManagementState> = _stateFlow.asStateFlow()


    fun updateScreenType(screenType: ScreenType) {
        _stateFlow.update {
            it.copy(
                screenType = screenType
            )
        }
    }
}