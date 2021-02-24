package com.teamounce.ounce.settings.util

import com.teamounce.ounce.settings.ui.SettingCustomDialog

class SettingCustomDialogBuilder {
    private val dialog = SettingCustomDialog()

    fun setTitle(title: String): SettingCustomDialogBuilder {
        dialog.title = title
        return this
    }

    fun setSubTitle(subtitle: String): SettingCustomDialogBuilder {
        dialog.subtitle = subtitle
        return this
    }

    fun setPositiveButton(positivebutton : String) : SettingCustomDialogBuilder {
        dialog.positiveButton = positivebutton
        return this
    }

    fun setNegativeButton(negativebutton : String) : SettingCustomDialogBuilder {
        dialog.negativeButton =negativebutton
        return this
    }
    fun setDisableNegativeButton(disableNegativeButton : Boolean): SettingCustomDialogBuilder {
        dialog.disableNegativeButton = disableNegativeButton
        return this
    }

    fun setDisablePositiveButton(disablePositiveButton: Boolean):SettingCustomDialogBuilder{
        dialog.disablePositiveButton = disablePositiveButton
        return this
    }

    fun setButtonClickListener(listener: SettingCustomDialogListener): SettingCustomDialogBuilder {
        dialog.listener = listener
        return this
    }

    fun create(): SettingCustomDialog {
        return dialog
    }
}