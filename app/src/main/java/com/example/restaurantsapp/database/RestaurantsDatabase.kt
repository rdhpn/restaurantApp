package com.example.restaurantsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RestaurantTable::class],
    version = 1
)

abstract class RestaurantsDatabase : RoomDatabase() {
    abstract fun getRestaurantsDAO(): RestaurantsDAO
}