package com.example.restaurantsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.Location
import com.example.restaurantsapp.model.domain.RestaurantDomain

@Entity(tableName = "restaurant")
data class RestaurantTable(
    @PrimaryKey
    val id: String,
    val image: String,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val address: String,
    val distance: Double
)

fun RestaurantDomain.mapToRestaurantTable(): RestaurantTable =
    RestaurantTable(
        this.id,
        this.image,
        this.name,
        this.phone,
        this.price,
        this.rating,
        this.address,
//        this.location.displayAddress.toString().split(" ").joinToString { "," },
        this.distance
    )

