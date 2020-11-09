package com.teamounce.ounce.login.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
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
        return binding.root
    }

    private fun setErrorText(constraint: Boolean, errorMsg: String) {
        if (constraint) {
            binding.txtlayoutRegisterName.hint = null
            binding.txtlayoutRegisterName.error = errorMsg
        } else {
            binding.txtlayoutRegisterName.hint = EDTITEXT_HINT
            binding.txtlayoutRegisterName.error = null
        }
    }

    companion object {
        private val REGEX_KOREAN = Regex("""^[ㄱ-ㅎ가-힣ㅏ-ㅣ]+${'$'}""")
        private const val EDTITEXT_HINT = "한글로 작성해주세요"
        private const val ERROR_BLANK = "이름을 입력해주세요"
        private const val ERROR_NOT_KOREAN = "이름은 한글만 가능합니다"
        private const val ERROR_EXCESS_LENGTH = "글자수를 초과했습니다"

    }
}