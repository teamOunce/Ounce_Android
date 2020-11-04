package com.teamounce.ounce.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamounce.ounce.databinding.FragmentOnboardingBinding
import com.teamounce.ounce.login.model.OnBoardingData

class OnBoardingFragment(private val onBoardingData: OnBoardingData) : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgOnboardingExplain
                .setImageResource(onBoardingData.img_onboarding_explain)
            txtOnboardingExplain.text = onBoardingData.txt_onboarding_explain
            txtOnboardingTitle.text = onBoardingData.txt_onboarding_title
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
