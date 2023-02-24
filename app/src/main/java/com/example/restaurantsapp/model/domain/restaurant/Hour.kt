package com.example.restaurantsapp.model.domain.restaurant


import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("hours_type")
    val hoursType: String? = null,
    @SerializedName("is_open_now")
    val isOpenNow: Boolean? = null,
    @SerializedName("open")
    val `open`: List<Open?>? = null
)