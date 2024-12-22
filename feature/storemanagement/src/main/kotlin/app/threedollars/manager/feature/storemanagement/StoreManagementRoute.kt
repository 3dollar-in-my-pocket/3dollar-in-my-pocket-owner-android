package app.threedollars.manager.feature.storemanagement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun StoreManagementRoute(
) {
    val viewModel: StoreManagementViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    StoreManagementScreen(
        screenType = uiState.screenType
    )
}
