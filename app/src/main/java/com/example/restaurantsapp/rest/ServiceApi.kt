package com.example.restaurantsapp.rest

import com.example.restaurantsapp.model.Business
import com.example.restaurantsapp.model.YelpResponse
import com.example.restaurantsapp.model.domain.reviews.ReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantsApi {

    @GET(SEARCH_PATH)
    suspend fun getHotNewRestaurants(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("term") term: String = "restaurants",
        @Query("attributes") attributes: String = "hot_and_new",
        @Query("sort_by") sort: String = "best_match",
        @Query("limit") limit: Int = 20,
    ): Response<YelpResponse>

    @GET(ID)
    suspend fun getRestaurantByID(
        @Path("id") id: String
    ): Response<Business>

    @GET(REVIEWS)
    suspend fun getReviewsByID(
        @Path("id") id: String
    ): Response<ReviewsResponse>

    companion object {
        const val BASE_URL = "https://api.yelp.com/v3/businesses/"
        private const val SEARCH_PATH = "search"
        private const val REVIEWS = "{id}/reviews"
        private const val ID = "{id}"
    }
}