package com.teamounce.ounce.register.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamounce.ounce.data.remote.repository.RegisterRepository
import com.teamounce.ounce.util.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
    val isMale = MutableLiveData<Boolean>()
    val isFemale = MutableLiveData<Boolean>()
    val agreePersonalInfoTerm = MutableLiveData<Boolean>()
    val userBirthYear = MutableLiveData(0)
    val isEnabledTransferToCatInfo = MediatorLiveData<Boolean>().apply {
        addSourceList(isMale, agreePersonalInfoTerm) { isValidTransferToCatInfo() }
    }

    private fun isValidTransferToCatInfo() =
        (isMale.value != null && isFemale.value != null) && agreePersonalInfoTerm.value!!
}