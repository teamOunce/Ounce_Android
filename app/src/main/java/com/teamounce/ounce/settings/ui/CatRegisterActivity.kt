package com.teamounce.ounce.settings.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityCatRegisterBinding
import com.teamounce.ounce.settings.ui.SettingsCareActivity.Companion.REGISTER_SUCCESS
import com.teamounce.ounce.settings.viewmodel.CatRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CatRegisterActivity :
    BindingActivity<ActivityCatRegisterBinding>(R.layout.activity_cat_register) {
    private val registerViewModel: CatRegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = registerViewModel
            lifecycleOwner = this@CatRegisterActivity
        }
        initView()
    }

    private fun initView() {
        setUIListener()
        subscribeDatas()
        setError()
    }

    private fun setUIListener() {
        binding.datepickerRegister.setOnDateChangedListener { _, year, month, day ->
            registerViewModel.meetDate = "$year-$month-$day"
        }
        binding.datepickerRegister.maxDate = Date().time
        binding.imgRegisterBack.setOnClickListener { finish() }
        binding.btnRegisterComplete.setOnClickListener { registerViewModel.registerCat() }
    }

    private fun subscribeDatas() {
        registerViewModel.apply {
            errorMessage.observe(this@CatRegisterActivity) {
                Toast.makeText(this@CatRegisterActivity, it, Toast.LENGTH_SHORT).show()
            }
            isSuccess.observe(this@CatRegisterActivity) {
                if (it) {
                    Toast.makeText(this@CatRegisterActivity, "고양이 등록에 성공했습니다", Toast.LENGTH_SHORT)
                        .show()
                    setResult(REGISTER_SUCCESS)
                    finish()
                }
            }
        }
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

    companion object {
        private val REGEX_KOREAN = Regex("""^[ㄱ-ㅎ가-힣ㅏ-ㅣ]+${'$'}""")
        private const val ERROR_BLANK = "이름을 입력해주세요"
        private const val ERROR_NOT_KOREAN = "이름은 한글만 가능합니다"
        private const val ERROR_EXCESS_LENGTH = "글자수를 초과했습니다"
    }
}