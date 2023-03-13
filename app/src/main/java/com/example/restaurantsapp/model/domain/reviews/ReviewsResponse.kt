package com.example.restaurantsapp.model.domain.reviews


import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("possible_languages")
    val possibleLanguages: List<String?>? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("total")
    val total: Int? = null
)