package com.teamounce.ounce.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.teamounce.ounce.main.BottomSheetProfileData
import java.text.FieldPosition

class SharedPreferences(context: Context) {
    private val prefs: SharedPreferences? =
        context.getSharedPreferences("prefs_name", android.content.Context.MODE_PRIVATE)
    var makeGson = GsonBuilder().create()
    var listType: TypeToken<MutableList<BottomSheetProfileData>> = object : TypeToken
    <MutableList<BottomSheetProfileData>>() {}

    private fun getInt(key: String): Int? {
        val int = prefs?.getInt(key, -1)

        return if (int == -1) {
            null
        } else {
            int
        }
    }

    private fun setInt(key: String, value: Int?) {
        if (value == null) {
            prefs?.edit()?.putInt(key, -1)?.apply()
        } else {
            prefs?.edit()?.putInt(key, value)?.apply()
        }
    }

    private fun getString(key: String): String? {
        val str = prefs?.getString(key, "null")

        return if (str.equals("null")) {
            null
        } else {
            return str
        }
    }

    private fun setString(key: String, value: String?) {
        prefs?.edit()?.putString(key, value)?.apply()
    }

    fun setCatList(value: MutableList<BottomSheetProfileData>) {
        var listToString = makeGson.toJson(value, listType.type)
        setString("your_cat_list", listToString)
    }

    fun getCatList(): MutableList<BottomSheetProfileData> {
        var catListToString = getString("your_cat_list")
        var catList: MutableList<BottomSheetProfileData> =
            makeGson.fromJson(catListToString, listType.type)
        return catList
    }

    fun setCatPositionSelected(position: Int) {
        return setInt("selectedCatPosition", position)
    }

    fun getCatPositionSelected(): Int? {
        return getInt("selectedCatPosition")
    }
}