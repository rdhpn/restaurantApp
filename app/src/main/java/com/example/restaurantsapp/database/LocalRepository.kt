package com.example.restaurantsapp.database

import android.util.Log
import com.example.restaurantsapp.utils.UIState
import com.example.restaurantsapp.database.RestaurantTable
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.reviews.Review
import javax.inject.Inject

private const val TAG = "LocalRepository"
interface LocalRepository {
    suspend fun insertRestaurant(restaurant: RestaurantDomain)
    suspend fun getRestaurants(): UIState<List<RestaurantTable>>
    suspend fun getRestaurantById(restaurantId: String): RestaurantTable?
//    suspend fun getReviewsById(restaurantId: String): UIState<List<Review>>
}

class LocalRepositoryImpl @Inject constructor(
    private val restaurantsDAO: RestaurantsDAO
): LocalRepository{

    override suspend fun getRestaurants(): UIState<List<RestaurantTable>> {
        return try {
            UIState.SUCCESS(restaurantsDAO.getRestaurants())
        } catch (e: Exception){
            UIState.ERROR(e)
        }
    }

    override suspend fun insertRestaurant(restaurant: RestaurantDomain) {
        try{
            val restaurantTable = restaurant.mapToRestaurantTable()
            restaurantsDAO.insertRestaurant(restaurantTable)
        }catch(e: Exception){
            Log.e(TAG, "insertRestaurant: ${e.localizedMessage}", e)
        }
    }

    override suspend fun getRestaurantById(RestaurantId: String): RestaurantTable? {
        return try {
            restaurantsDAO.getRestaurantById(RestaurantId)
        }catch (e: java.lang.Exception){
            Log.e(TAG, "getRestaurantById: ${e.localizedMessage}", e)
            null
        }
    }

}
