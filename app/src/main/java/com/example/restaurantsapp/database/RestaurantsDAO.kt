package com.example.restaurantsapp.database

import androidx.room.*

@Dao
interface RestaurantsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(vararg Restaurant: RestaurantTable)

    @Query("SELECT * FROM restaurant")
    suspend fun getRestaurants(): List<RestaurantTable>

    @Query("SELECT * FROM restaurant WHERE id = :idRestaurant ")
    suspend fun getRestaurantById(idRestaurant: String): RestaurantTable

}
