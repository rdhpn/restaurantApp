package com.example.restaurantsapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantsapp.databinding.ActivityMainBinding
import com.example.restaurantsapp.viewmodel.RestaurantViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        getCurrentLocation()
    }

//    private fun getCurrentLocation() : Pair<Double, Double> {
//        var latitude = 0.0
//        var longitude = 0.0
//
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    requestPermission()
//                    return
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
//                    val location: Location? = task.result
//                    if (location == null) {
//                        Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()
//                    }
//                    else {
//                        Toast.makeText(this, "Get Success", Toast.LENGTH_SHORT).show()
//                        latitude = location.latitude
//                        longitude = location.longitude
//                    }
//                }
//
//            }
//        } else {
//            Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            startActivity(intent)
//        }
//        return Pair(latitude, longitude)
//    }
//
//    private fun isLocationEnabled(): Boolean {
//        val locationManager: LocationManager =
//            getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }
//
//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            PERMISSION_REQUEST_ACCESS_LOCATION
//        )
//    }
//
//
//    companion object {
//        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
//    }
//
//    private fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//
//        return false
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
//                getCurrentLocation()
//            } else {
//                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
