package com.teamounce.ounce.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.remote.repository.SettingRepository
import com.teamounce.ounce.settings.model.RequestAddCatProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatRegisterViewModel @Inject constructor(
    private val repository: SettingRepository
) : ViewModel() {
    val catName = MutableLiveData("")
    var meetDate: String = ""
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    fun registerCat() {
        viewModelScope.launch {
            runCatching {
                repository.addCat(RequestAddCatProfile(catName.value!!, meetDate))
            }.onSuccess {
                when (it.data) {
                    null -> setErrorResponse(it.responseMessage)
                    else -> responseSuccess()
                }
            }.onFailure {
                setErrorResponse("서버 내부 에러")
            }
        }
    }

    private fun setErrorResponse(message: String) { _errorMessage.value = message }
    private fun responseSuccess() { _isSuccess.value = true }
}