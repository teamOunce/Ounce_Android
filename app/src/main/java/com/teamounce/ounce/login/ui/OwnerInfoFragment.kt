package com.teamounce.ounce.login.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentOwnerInfoBinding

class OwnerInfoFragment : Fragment() {
    private lateinit var binding: FragmentOwnerInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_info, container, false)
        binding.btnOwnerNext.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_ownerInfoFragment_to_registerFragment)
        }
        binding.txtSkip.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_ownerInfoFragment_to_registerFragment)
        }
        return binding.root
    }
}