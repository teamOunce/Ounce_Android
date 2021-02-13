package com.teamounce.ounce.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.LoginRepository
import com.teamounce.ounce.login.model.OnBoardingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _onBoardingInfoList = mutableListOf<OnBoardingData>()
    val onBoardingInfoList: List<OnBoardingData>
        get() = _onBoardingInfoList
    private val _isCatNull = MutableLiveData<Boolean>()
    val isCatNull: LiveData<Boolean>
        get() = _isCatNull

    init {
        initOnBoardingInfoList()
    }

    private fun initOnBoardingInfoList() {
        _onBoardingInfoList.apply {
            add(
                OnBoardingData(
                    img_onboarding_explain = "onboarding_img_01.json",
                    txt_onboarding_title = "집사를 위한 기록장",
                    txt_onboarding_explain = "우리 고양이의 입맛을 기록해요"
                )
            )
            add(
                OnBoardingData(
                    img_onboarding_explain = "onboarding_img_02.json",
                    txt_onboarding_title = "나만의 태그",
                    txt_onboarding_explain = "자신만의 기준으로 기록을 분류하세요"
                )
            )
            add(
                OnBoardingData(
                    img_onboarding_explain = "onboarding_img_03.json",
                    txt_onboarding_title = "행복의 여정",
                    txt_onboarding_explain = "차곡차곡 쌓인 기록들로\n다음 캣푸드를 찾아 떠나요"
                )
            )
        }
    }

    fun kakaoLogin(id: String) = viewModelScope.launch {
        runCatching {
            loginRepository.kakaoLogin(id)
        }.onSuccess {
            Log.d("TAG", "")
            OunceLocalRepository.apply {
                userAccessToken = it.data.token
                userRefreshToken = it.data.refresh
                catIndex = it.data.recentCatIndex
                loginFrom = this.KAKAO
            }
            when (it.data.catCount) {
                0 -> _isCatNull.value = true
                else -> _isCatNull.value = false
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun googleLogin(token: String) = viewModelScope.launch {
        runCatching {
            loginRepository.googleLogin(token)
        }.onSuccess {
            OunceLocalRepository.apply {
                userAccessToken = it.data.token
                userRefreshToken = it.data.refresh
                catIndex = it.data.recentCatIndex
                loginFrom = this.GOOGLE
            }
            when (it.data.catCount) {
                0 -> _isCatNull.value = true
                else -> _isCatNull.value = false
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}