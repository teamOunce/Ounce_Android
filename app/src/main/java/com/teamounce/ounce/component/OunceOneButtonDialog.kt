package com.teamounce.ounce.component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.teamounce.ounce.databinding.DialogOunceOneButtonBinding

class OunceOneButtonDialog(
    context: Context,
    private val title: String,
    private val contents: String,
    private val btnText: String,
    cancelable: Boolean = true
): Dialog(context) {

    private val binding by lazy {
        DialogOunceOneButtonBinding.inflate(layoutInflater)
    }

    private var clickListener: ClickListener? = null

    init {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
    }

    constructor(context: Context, title: String, contents: String, btnText: String)
            : this(context, title, contents, btnText, true)

    constructor(context: Context, title: String, contents: String)
            : this(context, title, contents, "확인")

    constructor(context: Context, title: String) : this(context, title, "")

    interface ClickListener {
        fun setOnClickOk(dialog: Dialog)
    }

    fun setOnDialogClickListener(l: ClickListener) {
        clickListener = l
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        window?.setBackgroundDrawableResource(R.drawable.dialog_background)


        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        binding.txtTitle.text = title
        if (contents.isBlank())
            binding.txtBody.visibility = View.GONE
        else
            binding.txtBody.text = contents
        binding.btnOk.text = btnText

        clickEvent()
    }

    private fun clickEvent() {
        binding.btnOk.setOnClickListener {
            clickListener?.setOnClickOk(this)
        }
    }

}