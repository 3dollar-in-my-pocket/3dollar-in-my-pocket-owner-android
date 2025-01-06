package app.threedollars.manager.feature.storemanagement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun StoreManagementRoute(
) {
    val viewModel: StoreManagementViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val feedbackSpecific = viewModel.feedbackSpecific.collectAsLazyPagingItems()

    val screenType = uiState.screenType
    LaunchedEffect(screenType) {
        viewModel.getBossStoreRetrieveMe()

        when (screenType) {
            ScreenType.REVIEW_INFO -> {
                viewModel.getFeedbackType()
                viewModel.getFeedbackFull()
            }

            ScreenType.PROFILE_EDIT -> {
                viewModel.getCategory()
            }

            ScreenType.ACCOUNT -> {
                viewModel.getBankEnum()
            }

            else -> {}
        }
    }
    StoreManagementScreen(
        screenType = screenType,
        dialogType = uiState.dialogType,
        bossStoreRetrieve = uiState.bossStoreRetrieve,
        storeCategories = uiState.storeCategories,
        selectedStoreCategories = uiState.selectedStoreCategories,
        bankTypes = uiState.bankTypes,
        errorMessage = uiState.errorMessage,
        scheduleDays = uiState.scheduleDays,
        appearanceDays = uiState.appearanceDays,
        feedbackFulls = uiState.feedbackFulls,
        feedbackTypes = uiState.feedbackTypes,
        feedbackSpecific = feedbackSpecific,
        onScreenTypeUpdate = { viewModel.updateScreenType(it) },
        onDialogTypeUpdate = { viewModel.updateDialogType(it) },
        onBossStorePatch = { viewModel.patchBossStore(it) },
        onMenuPatch = { viewModel.patchMenu(it) },
        onStoreCategorySelected = { viewModel.categorySelection(it) },
        onStartTimeUpdate = { day, time ->
            viewModel.updateDaysStartTime(
                day = day,
                time = time
            )
        },
        onEndTimeUpdate = { day, time ->
            viewModel.updateDaysEndTime(
                day = day,
                time = time
            )
        },
        onLocationDescriptionUpdate = { day, locationDescription ->
            viewModel.updateDaysLocationDescription(
                day = day,
                locationDescription = locationDescription
            )
        },
        onScheduleDayUpdate = { scheduleDay ->
            viewModel.updateScheduleDay(
                scheduleDay = scheduleDay
            )
        }

    )
}
