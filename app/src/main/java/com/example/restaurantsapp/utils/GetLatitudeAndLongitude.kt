package com.example.restaurantsapp.utils

import android.location.Location
import android.location.LocationListener


class GetLatitudeAndLongitude : LocationListener {
    var currentLatitude = 0.0
    var currentLongitude = 0.0
        override fun onLocationChanged(location: Location) {
            currentLatitude = location.latitude
            currentLongitude = location.longitude
        }
    }
