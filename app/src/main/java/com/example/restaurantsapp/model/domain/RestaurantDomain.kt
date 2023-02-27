package com.example.restaurantsapp.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restaurantsapp.database.RestaurantTable
import com.example.restaurantsapp.database.mapToRestaurantTable
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.Location
import com.example.restaurantsapp.model.domain.reviews.Review
import com.google.gson.Gson

data class RestaurantDomain(
    val id: String,
    val image: String,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val address: String,
    val distance: Double,
)

fun List<Business>?.mapToRestaurantDomain(): List<RestaurantDomain> =
    this?.map {
        RestaurantDomain(
            id = it.id ?: "ID not available",
            image = it.imageUrl ?: "not available",
            name = it.name ?: "not available",
            phone = it.displayPhone ?: "not available",
            price = it.price ?: "not available",
            rating = it.rating ?: 0.0,
            address = it.location?.displayAddress?.joinToString(separator = ",") ?: "no address",
            distance = it.distance ?: 0.0,
        )
    } ?: emptyList()

fun Business.mapToRestaurantDomain(): RestaurantDomain =
    RestaurantDomain(
        this.id ?: "no id available",
        this.imageUrl ?: "no imageUrl available",
        this.name ?: "no name available",
        this.phone ?: "no phone available",
        this.price ?: "no price available",
        this.rating ?: 0.0,
        address = this.location?.displayAddress?.joinToString(separator = ",") ?: "no address",
        this.distance?: 0.0
    )

fun List<RestaurantTable>.mapToRestaurantsFromTable(): List<RestaurantDomain> {
    val gson = Gson()
    return this.map {
        RestaurantDomain(
            it.id,
            it.image,
            it.name,
            it.phone,
            it.price,
            it.rating,
            it.address,
            it.distance
        )
    }
}

fun RestaurantTable.mapToRestaurantsFromTable(): RestaurantDomain =
    RestaurantDomain(
        this.id,
        this.image,
        this.name,
        this.phone,
        this.price,
        this.rating,
        this.address,
        this.distance
    )

