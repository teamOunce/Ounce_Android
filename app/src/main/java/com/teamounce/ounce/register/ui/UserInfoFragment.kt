package com.teamounce.ounce.register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingFragment
import com.teamounce.ounce.databinding.FragmentUserInfoBinding

class UserInfoFragment : BindingFragment<FragmentUserInfoBinding>(R.layout.fragment_user_info) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUIClickListener()
        binding.pickerOwnerBornYear.maxValue = 2020
        binding.pickerOwnerBornYear.minValue = 1900
        return binding.root
    }

    private fun setUIClickListener() {
        binding.btnOwnerNext.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_ownerInfoFragment_to_registerFragment)
        }
        binding.txtSkip.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_ownerInfoFragment_to_registerFragment)
        }
    }
}