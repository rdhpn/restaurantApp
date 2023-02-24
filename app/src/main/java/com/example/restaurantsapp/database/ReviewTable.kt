package com.example.restaurantsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.Location
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.mapToReviewDomain
import com.example.restaurantsapp.model.domain.reviews.Review

@Entity(tableName = "review")
data class ReviewTable(
    @PrimaryKey
    val name: String? = null,
    val userIconUrl: String? = null,
    val userRating: Int? = null,
    val dayOfReview: String? = null,
    val reviewContent: String? = null
)

fun Review?.mapToReviewTable(): ReviewTable =
    ReviewTable(
        name = this?.user?.name ?: " - ",
        userIconUrl = this?.user?.imageUrl ?: " - ",
        userRating = this?.rating,
        dayOfReview = this?.timeCreated!!.split(" ")[0],
        reviewContent = this?.text
    )

