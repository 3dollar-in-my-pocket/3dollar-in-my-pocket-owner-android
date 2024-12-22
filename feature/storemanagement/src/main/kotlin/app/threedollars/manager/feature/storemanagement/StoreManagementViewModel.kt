package app.threedollars.manager.feature.storemanagement

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
internal class StoreManagementViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow: MutableStateFlow<StoreManagementState> = MutableStateFlow(StoreManagementState())

    val stateFlow: StateFlow<StoreManagementState> = _stateFlow.asStateFlow()


}