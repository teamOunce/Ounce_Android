package com.teamounce.ounce.settings

interface SettingCustomDialogListener : SettingCustomDialog.SettingCustomDialogListener {
    override fun onClickPositiveButton()
    override fun onClickNegativeButton()
}