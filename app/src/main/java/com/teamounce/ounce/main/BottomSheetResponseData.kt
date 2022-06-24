package com.teamounce.ounce.main

import com.google.gson.annotations.SerializedName

data class BottomSheetResponseData (
    @SerializedName("reponseMessage")
    val responseMessage : String,
    @SerializedName("data")
    val data : MutableList<BottomSheetProfileData>
)