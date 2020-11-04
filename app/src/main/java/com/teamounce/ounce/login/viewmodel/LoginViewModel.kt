package com.teamounce.ounce.login.viewmodel

import androidx.lifecycle.ViewModel
import com.teamounce.ounce.R
import com.teamounce.ounce.login.model.OnBoardingData

class LoginViewModel : ViewModel() {
    private val _onBoardingInfoList = mutableListOf<OnBoardingData>()
    val onBoardingInfoList: List<OnBoardingData>
        get() = _onBoardingInfoList

    fun initOnBoardingInfoList() {
        _onBoardingInfoList.apply {
            add(
                OnBoardingData(
                    img_onboarding_explain = R.drawable.img_dummy,
                    txt_onboarding_title = "집사를 위한 기록장",
                    txt_onboarding_explain = "우리 고양이의 입맛을 기록해요"
                )
            )
            add(
                OnBoardingData(
                    img_onboarding_explain = R.drawable.img_dummy,
                    txt_onboarding_title = "나만의 태그",
                    txt_onboarding_explain = "자신만의 기준으로 기록을 분류하세요"
                )
            )
            add(
                OnBoardingData(
                    img_onboarding_explain = R.drawable.img_dummy,
                    txt_onboarding_title = "행복의 여정",
                    txt_onboarding_explain = "차곡차곡 쌓인 기록들로\n다음 캣푸드를 찾아 떠나요"
                )
            )
        }
    }
}