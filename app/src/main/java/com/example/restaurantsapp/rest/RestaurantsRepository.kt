package com.example.restaurantsapp.rest

import android.util.Log
import com.example.restaurantsapp.database.LocalRepository
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.ReviewDomain
import com.example.restaurantsapp.model.domain.mapToRestaurantDomain
import com.example.restaurantsapp.model.domain.mapToReviewDomain
import com.example.restaurantsapp.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "RestaurantsRepository"

interface RestaurantsRepository {
    fun getRestaurantByID(id: String): Flow<UIState<RestaurantDomain>>
    fun getReviewsByID(id: String): Flow<UIState<List<ReviewDomain>>>
    fun getHotNewRestaurants(latitude: Double, longtitude: Double): Flow<UIState<List<RestaurantDomain>>>
}

class RestaurantsRepositoryImpl @Inject constructor(
    private val serviceApi: RestaurantsApi,
    private val localRepository: LocalRepository,
//    private val currentLoc: GetLatitudeAndLongitude
) : RestaurantsRepository {

    override fun getHotNewRestaurants(latitude: Double, longtitude: Double): Flow<UIState<List<RestaurantDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = serviceApi.getHotNewRestaurants( latitude, longtitude )
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.businesses.mapToRestaurantDomain()))
                } ?: throw NullRestaurantResponse() //check if response was null
            } else throw FailureResponse(response.errorBody()?.string())
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
            Log.d(TAG, "getHotNewRestaurants: Error getting location")
        }
    }

    override fun getRestaurantByID(id: String): Flow<UIState<RestaurantDomain>> = flow {
        emit(UIState.LOADING)
        try {
            val response = serviceApi.getRestaurantByID(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.mapToRestaurantDomain()))
                } ?: throw NullRestaurantResponse() //check if response was null
            } else throw FailureResponse(response.errorBody()?.string())
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
            Log.e(TAG, "getRestaurantsByID: ${e.localizedMessage}", e)
        }
    }

    override fun getReviewsByID(id: String): Flow<UIState<List<ReviewDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = serviceApi.getReviewsByID(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    val reviewList: MutableList<ReviewDomain> = mutableListOf()
                    for (i in 0..2) {
                        if (it.reviews?.get(i) != null) it?.reviews[i]?.let { it1 ->
                            reviewList.add(
                                it1.mapToReviewDomain())
                        }
                    }
                    emit(UIState.SUCCESS(reviewList))
                } ?: throw NullReviewResponse() //check if response was null
            } else throw FailureResponse(response.errorBody()?.string())
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
            Log.e(TAG, "getReviewsByID: ${e.localizedMessage}", e)
        }
    }
}