package com.teamounce.ounce.main

import com.google.gson.annotations.SerializedName

data class BottomSheetProfileData (
    @SerializedName("catIndex")
    val catIndex : Int,
    @SerializedName("catName")
    val catName : String,
    @SerializedName("fromMeet")
    val fromMeet : Int,
    @SerializedName("state")
    val state : Boolean
)