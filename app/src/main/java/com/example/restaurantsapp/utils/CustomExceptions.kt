package com.example.restaurantsapp.utils

class NullRestaurantResponse(message: String = "Restaurant response is null") : Exception(message)
class FailureResponse(message: String?) : Exception(message)