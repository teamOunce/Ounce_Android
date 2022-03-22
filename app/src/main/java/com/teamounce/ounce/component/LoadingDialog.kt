package com.teamounce.ounce.component

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.teamounce.ounce.R

@SuppressLint("InflateParams")
class LoadingDialog(context: Context, isCancelable: Boolean = false) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // make dialog background transparent
        this.window!!.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        // make dialog background un-selectable
        setCanceledOnTouchOutside(isCancelable)
        setCancelable(isCancelable)

        setContentView(R.layout.dialog_loading)

    }
}