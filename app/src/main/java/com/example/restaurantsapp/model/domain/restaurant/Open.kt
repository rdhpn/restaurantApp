package com.example.restaurantsapp.model.domain.restaurant


import com.google.gson.annotations.SerializedName

data class Open(
    @SerializedName("day")
    val day: Int? = null,
    @SerializedName("end")
    val end: String? = null,
    @SerializedName("is_overnight")
    val isOvernight: Boolean? = null,
    @SerializedName("start")
    val start: String? = null
)