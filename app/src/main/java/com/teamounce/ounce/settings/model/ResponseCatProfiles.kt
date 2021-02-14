package com.teamounce.ounce.settings.model


import com.google.gson.annotations.SerializedName
import com.teamounce.ounce.main.BottomSheetProfileData

data class ResponseCatProfiles(
    @SerializedName("data")
    val `data`: List<BottomSheetProfileData>,
    @SerializedName("responseMessage")
    val responseMessage: String
)