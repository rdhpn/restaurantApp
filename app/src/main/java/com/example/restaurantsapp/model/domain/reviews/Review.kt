package com.example.restaurantsapp.model.domain.reviews


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("time_created")
    val timeCreated: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("user")
    val user: User? = null
)