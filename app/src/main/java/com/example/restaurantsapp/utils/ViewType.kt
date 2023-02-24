package com.example.restaurantsapp.utils

import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.ReviewDomain

sealed class ViewType {
    data class RESTAURANT(val restaurant: RestaurantDomain) : ViewType()
    data class REVIEW(val review: ReviewDomain) : ViewType()
}
