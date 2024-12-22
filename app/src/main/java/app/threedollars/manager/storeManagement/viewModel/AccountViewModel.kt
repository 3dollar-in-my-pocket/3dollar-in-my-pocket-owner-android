package app.threedollars.manager.storeManagement.viewModel

import androidx.lifecycle.viewModelScope
import app.threedollars.common.BaseViewModel
import app.threedollars.common.EventFlow
import app.threedollars.common.MutableEventFlow
import app.threedollars.domain.usecase.BossStoreRetrieveUseCase
import app.threedollars.domain.usecase.BossStoreUseCase
import app.threedollars.domain.usecase.EnumMapperUseCase
import app.threedollars.manager.util.dtoToVo
import app.threedollars.manager.vo.BossStoreRetrieveVo
import app.threedollars.manager.vo.EnumsVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val bossStoreRetrieveUseCase: BossStoreRetrieveUseCase,
    private val bossStoreUseCase: BossStoreUseCase,
    private val enumMapperUseCase: EnumMapperUseCase,
) : BaseViewModel() {

    private val _bossStoreRetrieveMe: MutableEventFlow<BossStoreRetrieveVo> = MutableEventFlow()
    val bossStoreRetrieveMe: EventFlow<BossStoreRetrieveVo> get() = _bossStoreRetrieveMe

    private val _bankEnum: MutableEventFlow<List<EnumsVo>> = MutableEventFlow()
    val bankEnum: EventFlow<List<EnumsVo>> get() = _bankEnum

    private val _editComplete = MutableEventFlow<Boolean>()
    val editComplete: EventFlow<Boolean> get() = _editComplete

    init {
        getBossStoreRetrieveMe()
        getBankEnum()
    }

    private fun getBossStoreRetrieveMe() {
        viewModelScope.launch(exceptionHandler) {
            bossStoreRetrieveUseCase.getBossStoreRetrieveMe().collect {
                if (it.code.toString() == "200") {
                    it.data?.let { data ->
                        _bossStoreRetrieveMe.emit(data.dtoToVo())
                    }
                }
                if (!it.errorMessage.isNullOrEmpty()) {
                    setErrorMessage(it.errorMessage.toString())
                }
            }
        }
    }

    fun patchBossStore(
        id: String,
        accountNumber: String,
        accountHolder: String,
        accountBank: String,
    ) {
        viewModelScope.launch(exceptionHandler) {
            bossStoreUseCase.patchBossStore(
                bossStoreId = id,
                accountNumber = accountNumber,
                accountHolder = accountHolder,
                accountBank = accountBank
            ).collect {
                if (it.code == "200") _editComplete.emit(true)
                if (!it.errorMessage.isNullOrEmpty()) {
                    setErrorMessage(it.errorMessage.toString())
                }
            }
        }
    }

    private fun getBankEnum() {
        viewModelScope.launch(exceptionHandler) {
            enumMapperUseCase.getBossEnums().collect {
                it.data?.let { data ->
                    _bankEnum.emit(data.dtoToVo().bankType)
                }
            }
        }
    }
}