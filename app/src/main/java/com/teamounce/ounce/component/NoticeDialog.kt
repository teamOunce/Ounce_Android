package com.teamounce.ounce.component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.WindowManager
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.DialogNoticeBinding

class NoticeDialog(context: Context): Dialog(context) {
    private val binding by lazy {
        DialogNoticeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        val text = binding.txt1.text
        val pointText = "23년 3월 31일을 기점"
        val startIndex = text.indexOf(pointText)
        val spannable = SpannableString(binding.txt1.text)

        spannable.run {
            setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                startIndex + pointText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.txt1.text = spannable

        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }
}