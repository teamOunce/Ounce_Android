package com.teamounce.ounce.register.ui

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingFragment
import com.teamounce.ounce.databinding.FragmentUserInfoBinding
import com.teamounce.ounce.register.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : BindingFragment<FragmentUserInfoBinding>(R.layout.fragment_user_info) {
    private val registerViewModel by activityViewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUIListener()
        setInitValue()
        setObserve()
        return binding.root
    }

    private fun setObserve() {
        registerViewModel.isEnabledTransferToCatInfo.observe(viewLifecycleOwner) {
            binding.btnOwnerNext.isEnabled = it
        }
    }

    private fun setInitValue() {
        binding.apply {
            viewModel = registerViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        binding.pickerOwnerBornYear.apply {
            maxValue = 2020
            minValue = 1985
            value = 1990
            displayedValues = (1985..2020).map { "$it 년" }.toTypedArray()
        }
        val textUserInfo = "개인정보 처리방침에 동의합니다"
        val spannableTextUserInfo = SpannableString(textUserInfo)
        spannableTextUserInfo.setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                Toast.makeText(requireContext(), "토오스트", Toast.LENGTH_SHORT).show()
            }
        }, 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableTextUserInfo.setSpan(UnderlineSpan(), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtUserTermInfo.apply {
            text = spannableTextUserInfo
            isClickable = true
            movementMethod = LinkMovementMethod.getInstance()
        }

    }

    private fun setUIListener() {
        binding.btnOwnerNext.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_ownerInfoFragment_to_registerFragment)
        }
        binding.btnOwnerMale.setOnCheckedChangeListener { _, value ->
            if (value && binding.btnOwnerFemale.isChecked) {
                binding.btnOwnerFemale.isChecked = !value
            }
            setButtonValue(value, binding.btnOwnerFemale.isSelected)
        }
        binding.btnOwnerFemale.setOnCheckedChangeListener { _, value ->
            if (value && binding.btnOwnerMale.isChecked) {
                binding.btnOwnerMale.isChecked = !value
            }
            setButtonValue(binding.btnOwnerMale.isSelected, value)
        }
        binding.pickerOwnerBornYear.setOnValueChangedListener { _, _, newVal ->
            registerViewModel.userBirthYear.value = newVal
        }
    }

    private fun setButtonValue(isMale: Boolean, isFemale: Boolean) {
        registerViewModel.isMale.value = isMale
        registerViewModel.isFemale.value = isFemale
    }
}