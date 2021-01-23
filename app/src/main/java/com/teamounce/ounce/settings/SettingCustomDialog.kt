package com.teamounce.ounce.settings

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.teamounce.ounce.R
import kotlinx.android.synthetic.main.fragment_setting_catdltdialog.*

class SettingCustomDialog : DialogFragment(){
    var title :String?= null
    var subtitle :String?= null
    var positiveButton : String?= null
    var negativeButton : String?= null
    var listener : SettingCustomDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_setting_catdltdialog,container,false)
        return view.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            findViewById<TextView>(R.id.dialog_title)?.text = title
            findViewById<TextView>(R.id.dialog_subtitle)?.text = subtitle
            findViewById<TextView>(R.id.settingcare_dialog_yes)?.text = positiveButton

            findViewById<TextView>(R.id.settingcare_dialog_yes)?.setOnClickListener{
                dismiss()
                listener?.onClickPositiveButton()
            }

            findViewById<TextView>(R.id.settingcare_dialog_no)?.text = negativeButton

            findViewById<TextView>(R.id.settingcare_dialog_no)?.setOnClickListener{
                dismiss()
                listener?.onClickNegativeButton()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 레이아웃 크기 및 위치 조정
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setGravity(Gravity.CENTER)
        cardview_dialog.setBackgroundResource(R.drawable.dialog_box)

    }

}