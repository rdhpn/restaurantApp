package com.example.restaurantsapp.model.domain.restaurant


import com.google.gson.annotations.SerializedName

data class Messaging(
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("use_case_text")
    val useCaseText: String? = null
)