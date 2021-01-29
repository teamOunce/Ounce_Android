package com.teamounce.ounce.util

import android.content.Context
import android.content.SharedPreferences
import java.text.FieldPosition

class SharedPreferences(context: Context) {
    private val prefs : SharedPreferences? =
        context.getSharedPreferences("prefs_name", android.content.Context.MODE_PRIVATE)

    private fun getInt(key :String) : Int? {
        val int = prefs?.getInt(key,-1)

        return if (int == -1){
            null
        }else {
            int
        }
    }

    private fun setInt (key:String, value: Int?) {
        if(value == null){
            prefs?.edit()?.putInt(key,-1)?.apply()
        }else{
            prefs?.edit()?.putInt(key,value)?.apply()
        }
    }

    fun setCatPositionSelected(position: Int) {
        return setInt("selectedCatPosition",position)
    }
    fun getCatPositionSelected (): Int? {
        return getInt("selectedCatPosition")
    }
}