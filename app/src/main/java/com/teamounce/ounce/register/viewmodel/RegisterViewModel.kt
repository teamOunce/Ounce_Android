package com.teamounce.ounce.register.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.RegisterRepository
import com.teamounce.ounce.register.model.RequestAddCatInfo
import com.teamounce.ounce.util.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
    private val _transferToMain = MutableLiveData<Boolean>()
    val transferToMain: LiveData<Boolean>
        get() = _transferToMain
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val isMale = MutableLiveData<Boolean>()
    val isFemale = MutableLiveData<Boolean>()
    val agreePersonalInfoTerm = MutableLiveData(false)
    val userBirthYear = MutableLiveData(0)
    val isEnabledTransferToCatInfo = MediatorLiveData<Boolean>().apply {
        addSourceList(isMale, agreePersonalInfoTerm) { isValidTransferToCatInfo() }
    }
    val catName = MutableLiveData("")
    val meetDate = MutableLiveData<String>()

    private fun isValidTransferToCatInfo() =
        (isMale.value != null && isFemale.value != null) && agreePersonalInfoTerm.value!!

    fun registerCat() {
        viewModelScope.launch {
            runCatching {
                val requestAddCatInfo = RequestAddCatInfo(
                    birth = userBirthYear.value.toString(),
                    catName = catName.value ?: "",
                    gender = genderConverter(isMale.value ?: true),
                    meetDate = meetDate.value ?: ""
                )
                Log.d("TAG", "$requestAddCatInfo")
                registerRepository.registerCatInfo(
                    RequestAddCatInfo(
                        birth = userBirthYear.value.toString(),
                        catName = catName.value ?: "",
                        gender = genderConverter(isMale.value ?: true),
                        meetDate = meetDate.value ?: ""
                    )
                )
            }.onSuccess {
                if (it.data != null) {
                    OunceLocalRepository.catIndex = it.data
                    _transferToMain.value = true
                } else {
                    _errorMessage.value = it.responseMessage
                }
            }.onFailure {}
        }
    }

    private fun genderConverter(isMan: Boolean): String = if (isMan) "m" else "f"
}