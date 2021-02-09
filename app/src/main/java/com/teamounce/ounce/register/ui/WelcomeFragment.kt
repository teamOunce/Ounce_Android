package com.teamounce.ounce.register.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingFragment
import com.teamounce.ounce.databinding.FragmentWelcomeBinding
import com.teamounce.ounce.register.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BindingFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {
    private val registerViewModel by activityViewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}