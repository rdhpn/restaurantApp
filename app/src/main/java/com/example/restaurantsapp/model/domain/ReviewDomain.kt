package com.example.restaurantsapp.model.domain

import com.example.restaurantsapp.R
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.Location
import com.example.restaurantsapp.model.domain.reviews.Review
import com.example.restaurantsapp.model.domain.reviews.User

data class ReviewDomain(
    val name: String? = null,
    val userIconUrl: String? = null,
    val userRating: Int? = null,
    val dayOfReview: String? = null,
    val reviewContent: String? = null
)

fun Review.mapToReviewDomain(): ReviewDomain =
    ReviewDomain(
        name = this.user?.name ?: " - ",
        userIconUrl = this.user?.imageUrl ?: " - ",
        userRating = this.rating,
        dayOfReview = this?.timeCreated?.split(" ")?.get(0) ?: " - ",
        reviewContent = this.text
    )