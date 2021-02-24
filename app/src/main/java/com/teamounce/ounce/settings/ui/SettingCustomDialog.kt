package com.teamounce.ounce.settings.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.teamounce.ounce.R
import kotlinx.android.synthetic.main.fragment_setting_catdltdialog.*

class SettingCustomDialog() : DialogFragment() {
    var title: String? = null
    var subtitle: String? = null
    var positiveButton: String? = null
    var negativeButton: String? = null
    var disableNegativeButton : Boolean?= false
    var disablePositiveButton : Boolean?= false
    var listener: SettingCustomDialogListener? = null
    private var deviceSizeX: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_setting_catdltdialog, container, false)

        //customdialog
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return view.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            if(disableNegativeButton == true) {
                settingcare_dialog_no.visibility = View.GONE
            }
            else{
                settingcare_dialog_no.visibility = View.VISIBLE
            }

            if(disablePositiveButton == true) {
                settingcare_dialog_yes.visibility = View.GONE
            }
            else{
                settingcare_dialog_yes.visibility = View.VISIBLE
            }

            findViewById<TextView>(R.id.dialog_title)?.text = title

            if(subtitle.isNullOrBlank()) {
                dialog_subtitle.visibility = View.GONE
            } else {
                dialog_subtitle.visibility = View.VISIBLE
                findViewById<TextView>(R.id.dialog_subtitle)?.text = subtitle
            }

            findViewById<TextView>(R.id.settingcare_dialog_yes)?.text = positiveButton

            findViewById<TextView>(R.id.settingcare_dialog_yes)?.setOnClickListener {
                dismiss()
                listener?.onClickPositiveButton()
            }

            findViewById<TextView>(R.id.settingcare_dialog_no)?.text = negativeButton

            findViewById<TextView>(R.id.settingcare_dialog_no)?.setOnClickListener {
                dismiss()
                listener?.onClickNegativeButton()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (deviceSizeX == null) {
            if(context == null) {
                return
            }
            val size = Point()
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R){
                context?.display
            } else{
                (context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            }?.getRealSize(size)
            deviceSizeX = size.x
        }
        if(deviceSizeX == null){
            return
        }
        val params = dialog?.window?.attributes
        params?.width = (deviceSizeX!!* DIALOG_WIDTH_RATIO).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }

    companion object{
        private const val DIALOG_WIDTH_RATIO = 0.8
    }

    interface SettingCustomDialogListener {
        fun onClickPositiveButton()
        fun onClickNegativeButton()

    }
}