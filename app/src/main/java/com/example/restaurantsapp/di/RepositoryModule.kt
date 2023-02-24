package com.example.restaurantsapp.di

import com.example.restaurantsapp.database.LocalRepository
import com.example.restaurantsapp.database.LocalRepositoryImpl
import com.example.restaurantsapp.rest.RestaurantsRepository
import com.example.restaurantsapp.rest.RestaurantsRepositoryImpl
import com.example.restaurantsapp.utils.GetLatitudeAndLongitude
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent:: class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRestaurantsRepository(
        restaurantRepositoryImpl: RestaurantsRepositoryImpl
    ): RestaurantsRepository

    @Binds
    abstract fun provideLocalRepo(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository

}
