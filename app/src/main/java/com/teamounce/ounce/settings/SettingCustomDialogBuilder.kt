package com.teamounce.ounce.settings

class SettingCustomDialogBuilder {
    private val dialog = SettingCustomDialog()

    fun setTitle(title: String): SettingCustomDialogBuilder{
        dialog.title = title
        return this
    }

    fun setSubTitle(subtitle: String): SettingCustomDialogBuilder{
        dialog.subtitle = subtitle
        return this
    }

    fun setPositiveButton(positivebutton : String) : SettingCustomDialogBuilder{
        dialog.positiveButton = positivebutton
        return this
    }

    fun setNegativeButton(negativebutton : String) : SettingCustomDialogBuilder{
        dialog.negativeButton =negativebutton
        return this
    }
    fun setSingleButton(singleButton : Boolean): SettingCustomDialogBuilder {
        dialog.singleButton = singleButton
        return this
    }

    fun setButtonClickListener(listener: SettingCustomDialogListener):SettingCustomDialogBuilder{
        dialog.listener = listener
        return this
    }

    fun create(): SettingCustomDialog{
        return dialog
    }
}