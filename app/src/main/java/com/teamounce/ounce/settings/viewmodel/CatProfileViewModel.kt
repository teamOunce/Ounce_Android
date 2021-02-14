package com.teamounce.ounce.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.SettingRepository
import com.teamounce.ounce.main.BottomSheetProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatProfileViewModel @Inject constructor(
    private val repository: SettingRepository
) : ViewModel() {
    private val _catProfileList = MutableLiveData<List<BottomSheetProfileData>>()
    val catProfileList: LiveData<List<BottomSheetProfileData>>
        get() = _catProfileList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getCatProfiles() {
        viewModelScope.launch {
            runCatching { repository.getCatProfiles(OunceLocalRepository.catIndex)}
                .onSuccess { _catProfileList.value = it.data }
                .onFailure { setErrorResponse("고양이 목록을 불러오는데 실패했습니다") }
        }
    }

    private fun setErrorResponse(message: String) { _errorMessage.value = message }
}