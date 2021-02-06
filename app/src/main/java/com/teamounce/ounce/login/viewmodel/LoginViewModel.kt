package com.teamounce.ounce.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.teamounce.ounce.R
import com.teamounce.ounce.data.remote.repository.LoginRepository
import com.teamounce.ounce.login.model.OnBoardingData

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _onBoardingInfoList = mutableListOf<OnBoardingData>()
    val onBoardingInfoList: List<OnBoardingData>
        get() = _onBoardingInfoList

    private var _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

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

    fun setCurrentUser(user: FirebaseUser?) {
        _currentUser.value = user
    }
}