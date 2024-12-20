package app.threedollars.manager.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun HomeRoute(
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    HomeScreen(
        location = uiState.location,
        address = uiState.address,
        bossStoreRetrieveMe = uiState.bossStoreRetrieveMe,
        bossStoreRetrieveArounds = uiState.bossStoreRetrieveArounds,
        onBossStoreAroundUpdate = { viewModel.getBossStoreAround(it) },
        onAddressUpdate = { viewModel.updateAddress(it) },
        onStoreStateUpdate = {
            when(it){
                StoreStateType.OPEN -> {
                    viewModel.storeOpen()
                }
                StoreStateType.CLOSE ->{
                    viewModel.storeClosed()
                }
            }
        }
    )
}
