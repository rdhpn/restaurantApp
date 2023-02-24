package com.example.restaurantsapp.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.restaurantsapp.database.RestaurantsDAO
import com.example.restaurantsapp.database.RestaurantsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesRestaurantDatabase(
        @ApplicationContext context: Context
    ): RestaurantsDatabase =
        Room.databaseBuilder(
            context,
            RestaurantsDatabase::class.java,
            "restaurant-db"
        ).build()

    @Provides
    fun providesRestaurantDao(
        restaurantsDatabase: RestaurantsDatabase
    ): RestaurantsDAO = restaurantsDatabase.getRestaurantsDAO()


}