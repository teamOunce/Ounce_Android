package com.teamounce.ounce.register.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingFragment
import com.teamounce.ounce.databinding.FragmentWelcomeBinding

class WelcomeFragment : BindingFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}