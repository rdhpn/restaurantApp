package com.example.restaurantsapp.model.domain.restaurant


import com.google.gson.annotations.SerializedName

data class SpecialHour(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("end")
    val end: String? = null,
    @SerializedName("is_closed")
    val isClosed: Any? = null,
    @SerializedName("is_overnight")
    val isOvernight: Boolean? = null,
    @SerializedName("start")
    val start: String? = null
)