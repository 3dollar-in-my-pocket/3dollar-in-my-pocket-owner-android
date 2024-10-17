package app.threedollars.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    private val _isLoading: MutableEventFlow<Boolean> = MutableEventFlow()
    val isLoading: EventFlow<Boolean> = _isLoading

    private val _errorMessage: MutableEventFlow<ValueWrapper<String>> = MutableEventFlow()
    val errorMessage: EventFlow<ValueWrapper<String>> = _errorMessage.asEventFlow()

    fun setLoading(value: Boolean) {
        viewModelScope.launch {
            _isLoading.emit(value)
        }
    }

    fun setErrorMessage(text: String) {
        viewModelScope.launch {
            val gson = Gson()
            val responseData = gson.fromJson(text, ErrorResponse::class.java)
            val message = ValueWrapper(responseData.message)
            _errorMessage.emit(message)
        }
    }
}