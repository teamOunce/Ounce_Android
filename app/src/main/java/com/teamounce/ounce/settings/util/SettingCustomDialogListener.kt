package com.teamounce.ounce.settings.util

import com.teamounce.ounce.settings.ui.SettingCustomDialog

interface SettingCustomDialogListener : SettingCustomDialog.SettingCustomDialogListener {
    override fun onClickPositiveButton()
    override fun onClickNegativeButton()
}