package com.teamounce.ounce.login.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivityLoginBinding
import com.teamounce.ounce.login.viewmodel.LoginViewModel
import com.teamounce.ounce.util.TransparentStatusBarObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        TransparentStatusBarObject.setStatusBar(this)
        val onBoardingPagerAdapter = ScreenSlidePagerAdapter(this)
        binding.vpLoginOnboarding.adapter = onBoardingPagerAdapter

        binding.dotsLoginOnboarding.setViewPager2(binding.vpLoginOnboarding)

        binding.btnLoginKakao.setOnClickListener {
            kakaoLoginCall(this)
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val onBoardingInfoList = loginViewModel.onBoardingInfoList
        override fun getItemCount() = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return OnBoardingFragment(onBoardingInfoList[position])
        }

    }

    private fun kakaoLoginCall(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null)
                Log.e("Kakao", "로그인 실패", error)
            else if (token != null) {
                Log.i(
                    "Kakao",
                    "로그인 성공 ${token.accessToken}, ${token.refreshToken}, ${token.scopes}"
                )
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("Kakao", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        if(user.kakaoAccount?.emailNeedsAgreement == false)
                            Log.d("Kakao", "사용자계정에 이메일 없음")
                        else if(user.kakaoAccount?.emailNeedsAgreement == true) {
                            Log.d("Kakao", "사용자에게")
                        }
                        Log.i(
                            "Kakao",
                            "${user.id}, ${user.kakaoAccount?.email}, ${user.kakaoAccount?.profile?.nickname}"
                        )
                    }
                }
            }

        }
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)

        } else {
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    companion object {
        private const val NUM_PAGES = 3
    }
}