package com.example.restaurantsapp.view


import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsapp.databinding.RestaurantRecyclerViewBinding
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.utils.BaseFragment
import com.example.restaurantsapp.utils.UIState
import com.example.restaurantsapp.utils.ViewType
import com.example.restaurantsapp.view.adapter.RestaurantAdapter
import com.google.android.gms.location.*


private const val TAG = "RestaurantsFragment"
private const val REQUEST_LOCATION_PERMISSION = 1


class RestaurantsFragment : BaseFragment() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val binding by lazy {
        RestaurantRecyclerViewBinding.inflate(layoutInflater)
    }

    private val restaurantAdapter by lazy {
        RestaurantAdapter { item ->
            restaurantViewModel.id = item.id
            restaurantViewModel.insertRestaurant(item)
            findNavController().navigate(com.example.restaurantsapp.R.id.action_restaurants_to_details)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize location client
        client = LocationServices
            .getFusedLocationProviderClient(
                requireActivity()
            )
        // check condition
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            // When permission is granted
            // Call method
            currentLocation
        } else {
            // When permission is not granted
            // Call method
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }

        Log.d(TAG, "onCreateView: ")
        // Inflate the layout for this fragment
        currentLocation
        restaurantViewModel.restaurantsByLocation.observe(viewLifecycleOwner) { state ->
            val listVT: MutableList<ViewType> = mutableListOf()
            when (state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS<List<RestaurantDomain>> -> {
                    state.response.forEach {
                        listVT.add(ViewType.RESTAURANT(it))
                    }
                    restaurantAdapter.updateItems(listVT)
                }
                is UIState.ERROR -> {
                    showError(state.error.localizedMessage) {
                        // todo define an action
                    }
                }
            }
        }
        binding.rvRestaurants.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = restaurantAdapter
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        restaurantViewModel.fragmentState = true
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        restaurantViewModel.fragmentState = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    // Initialize variables
    var btLocation: Button? = null
    var tvLatitude: TextView? = null
    var tvLongitude: TextView? = null
    var client: FusedLocationProviderClient? = null

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        // Check condition
        if (requestCode == 100 && grantResults.size > 0
            && (grantResults[0] + grantResults[1]
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            // When permission are granted
            // Call  method
            currentLocation
        } else {
            // When permission are denied
            // Display toast
            Toast
                .makeText(
                    activity,
                    "Permission denied",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }// When location service is not enabled
    // open location setting
// Initialize
    // location
    // Set latitude
    // Set longitude

    // Request location updates
// When location result is null
    // initialize location request

    // Initialize location call back
// When location result is not
    // null set latitude
    // set longitude
// Initialize location
    // Check condition
// When location service is enabled
    // Get last location
    // Initialize Location manager
    @get:SuppressLint("MissingPermission")
    private val currentLocation:
    // Check condition
            Unit
        private get() {
            // Initialize Location manager
            val locationManager = activity?.getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager
            // Check condition
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ) {
                // When location service is enabled
                // Get last location
                client!!.lastLocation.addOnCompleteListener { task ->
                    // Initialize location
                    val location = task.result
                    // Check condition
                    if (location != null) {
                        // When location result is not
                        // null set latitude
//                        tvLatitude!!.text = location.latitude.toString()
                        // set longitude
//                        tvLongitude!!.text = location.longitude.toString()
                        restaurantViewModel.getRestaurantsByLocation(
                            location?.getLatitude(), location?.getLongitude()
                        )
                    } else {
                        // When location result is null
                        // initialize location request
                        val locationRequest = LocationRequest()
                            .setPriority(
                                LocationRequest.PRIORITY_HIGH_ACCURACY
                            )
                            .setInterval(10000)
                            .setFastestInterval(
                                1000
                            )
                            .setNumUpdates(1)

                        // Initialize location call back
                        val locationCallback: LocationCallback = object : LocationCallback() {
                            override fun onLocationResult(
                                locationResult: LocationResult
                            ) {
                                // Initialize
                                // location
                                val location1 = locationResult.lastLocation
//                                private var lat=0.0
//                                private var lon=0.0
                                // Set latitude
//                                tvLatitude?.text = location1?.getLatitude().toString()
                                // Set longitude
//                                tvLongitude?.text = location1?.getLongitude().toString()

                                // Request location updates
                                restaurantViewModel.getRestaurantsByLocation(
                                    location1?.getLatitude(), location1?.getLongitude()
                                )
//        getCurrentLocation()
                            }
                        }
//                        client!!.requestLocationUpdates( locationRequest, locationCallback, Looper.myLooper() )
                    }
                }
            } else {
                // When location service is not enabled
                // open location setting
                startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
}