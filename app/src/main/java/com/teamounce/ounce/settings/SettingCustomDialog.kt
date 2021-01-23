package com.teamounce.ounce.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.teamounce.ounce.R

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
        return super.onCreateView(inflater, container, savedInstanceState)
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

}