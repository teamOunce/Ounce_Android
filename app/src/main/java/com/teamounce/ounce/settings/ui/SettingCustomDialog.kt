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
import com.teamounce.ounce.databinding.FragmentSettingCatdltdialogBinding

class SettingCustomDialog() : DialogFragment() {
    var title: String? = null
    var subtitle: String? = null
    var positiveButton: String? = null
    var negativeButton: String? = null
    var disableNegativeButton: Boolean? = false
    var disablePositiveButton: Boolean? = false
    var listener: SettingCustomDialogListener? = null
    private var deviceSizeX: Int? = null
    private var _binding: FragmentSettingCatdltdialogBinding? = null
    private val binding: FragmentSettingCatdltdialogBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingCatdltdialogBinding.inflate(inflater, container, false)

        //customdialog
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            if (disableNegativeButton == true) {
                binding.settingcareDialogNo.visibility = View.GONE
            } else {
                binding.settingcareDialogNo.visibility = View.VISIBLE
            }

            if (disablePositiveButton == true) {
                binding.settingcareDialogYes.visibility = View.GONE
            } else {
                binding.settingcareDialogYes.visibility = View.VISIBLE
            }

            findViewById<TextView>(R.id.dialog_title)?.text = title

            if (subtitle.isNullOrBlank()) {
                binding.dialogSubtitle.visibility = View.GONE
            } else {
                binding.dialogSubtitle.visibility = View.VISIBLE
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
            if (context == null) {
                return
            }
            val size = Point()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                context?.display
            } else {
                (context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            }?.getRealSize(size)
            deviceSizeX = size.x
        }
        if (deviceSizeX == null) {
            return
        }
        val params = dialog?.window?.attributes
        params?.width = (deviceSizeX!! * DIALOG_WIDTH_RATIO).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }

    companion object {
        private const val DIALOG_WIDTH_RATIO = 0.8
    }

    interface SettingCustomDialogListener {
        fun onClickPositiveButton()
        fun onClickNegativeButton()

    }
}