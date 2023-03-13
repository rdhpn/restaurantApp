package com.example.restaurantsapp.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.database.LocalRepository
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.ReviewDomain
import com.example.restaurantsapp.rest.RestaurantsRepository
import com.example.restaurantsapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RestaurantViewModel"

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val restaurantRepository: RestaurantsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val localRepository: LocalRepository
) : ViewModel() {

    var id = ""
    var fragmentState: Boolean = false
    var latitude = 0.0
    var Longitude = 0.0

    private val _restaurantsByLocation: MutableLiveData<UIState<List<RestaurantDomain>>> =
        MutableLiveData(UIState.LOADING)
    val restaurantsByLocation: LiveData<UIState<List<RestaurantDomain>>> get() = _restaurantsByLocation

    private val _restaurantById: MutableLiveData<UIState<RestaurantDomain?>> =
        MutableLiveData(UIState.LOADING)
    val restaurantById: LiveData<UIState<RestaurantDomain?>> get() = _restaurantById

    private val _reviewsById: MutableLiveData<UIState<List<ReviewDomain?>>> =
        MutableLiveData(UIState.LOADING)
    val reviewsById: LiveData<UIState<List<ReviewDomain?>>> get() = _reviewsById

    fun getRestaurantsByLocation(latitude: Double?=null, longtitude: Double?=null) {
        if (latitude !=null && longtitude !=null) {
        viewModelScope.launch(ioDispatcher) {
            restaurantRepository.getHotNewRestaurants(latitude, longtitude).collect {
                _restaurantsByLocation.postValue(it)
                Log.d(ContentValues.TAG, "TEST: $_restaurantsByLocation")
            }
        }
        }
    }

    fun getRestaurantById(id: String? = null) {
        id?.let {
            viewModelScope.launch(ioDispatcher) {
                restaurantRepository.getRestaurantByID(id).collect {
                    _restaurantById.postValue(it)
                    Log.d(ContentValues.TAG, "TEST: $_restaurantById")
                }
            }
        }
    }

    fun getReviewsById(id: String? = null) {
        id?.let {
            viewModelScope.launch(ioDispatcher) {
                restaurantRepository.getReviewsByID(id).collect {
                    _reviewsById.postValue(it)
                    Log.d(ContentValues.TAG, "TEST: $_restaurantById")
                }
            }
        }
    }

    fun insertRestaurant(restaurantDomain: RestaurantDomain) {
        viewModelScope.launch(ioDispatcher) {
            localRepository.insertRestaurant(restaurantDomain)
        }
    }
}
