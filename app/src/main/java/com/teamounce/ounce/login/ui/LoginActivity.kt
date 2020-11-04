package com.teamounce.ounce.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivityLoginBinding
import com.teamounce.ounce.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loginViewModel.initOnBoardingInfoList()

        val onBoardingPagerAdapter = ScreenSlidePagerAdapter(this)
        binding.vpLoginOnboarding.adapter = onBoardingPagerAdapter

        binding.dotsLoginOnboarding.setViewPager2(binding.vpLoginOnboarding)
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val onBoardingInfoList = loginViewModel.onBoardingInfoList
        override fun getItemCount() = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return OnBoardingFragment(onBoardingInfoList[position])
        }

    }

    companion object {
        private const val NUM_PAGES = 3
    }
}