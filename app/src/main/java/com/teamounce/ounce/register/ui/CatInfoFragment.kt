package com.teamounce.ounce.register.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingFragment
import com.teamounce.ounce.databinding.FragmentCatInfoBinding
import com.teamounce.ounce.register.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import java.util.*

@AndroidEntryPoint
class CatInfoFragment : BindingFragment<FragmentCatInfoBinding>(R.layout.fragment_cat_info) {
    private val registerViewModel by activityViewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.apply {
            viewModel = registerViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setUIListener()
        setError()
        setMaxDate()
        observeKeyboard()
        return binding.root
    }

    private fun setUIListener() {
        binding.datepickerRegister.setOnDateChangedListener { _, year, month, day ->
            registerViewModel.meetDate.value = "$year-$month-$day"
        }
        binding.btnRegisterComplete.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_registerFragment_to_welcomeFragment)
        }
    }

    private fun setMaxDate() {
        binding.datepickerRegister.maxDate = Date().time
    }

    private fun setError() {
        binding.txtCatName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(catName: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (catName!!.length > binding.txtlayoutRegisterName.counterMaxLength) {
                    binding.txtlayoutRegisterName.error = ERROR_EXCESS_LENGTH
                    binding.btnRegisterComplete.isEnabled = false
                } else if (catName.isEmpty()) {
                    binding.txtlayoutRegisterName.error = ERROR_BLANK
                    binding.btnRegisterComplete.isEnabled = false
                } else if (!REGEX_KOREAN.matches(catName)) {
                    binding.txtlayoutRegisterName.error = ERROR_NOT_KOREAN
                    binding.btnRegisterComplete.isEnabled = false
                } else {
                    binding.txtlayoutRegisterName.error = null
                    binding.btnRegisterComplete.isEnabled = true
                }
            }

            override fun afterTextChanged(catName: Editable?) {}

        })
    }

    private fun observeKeyboard() {
        activity?.let {
            TedKeyboardObserver(it)
                .listen { isShow ->
                    if (!isShow) { binding.txtCatName.clearFocus() }
                }
        }
    }

    companion object {
        private val REGEX_KOREAN = Regex("""^[ㄱ-ㅎ가-힣ㅏ-ㅣ]+${'$'}""")
        private const val ERROR_BLANK = "이름을 입력해주세요"
        private const val ERROR_NOT_KOREAN = "이름은 한글만 가능합니다"
        private const val ERROR_EXCESS_LENGTH = "글자수를 초과했습니다"
    }
}