package com.example.restaurantsapp.view


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsapp.databinding.RestaurantRecyclerViewBinding
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.utils.BaseFragment
import com.example.restaurantsapp.utils.UIState
import com.example.restaurantsapp.utils.ViewType
import com.example.restaurantsapp.view.adapter.RestaurantAdapter
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks


private const val TAG = "RestaurantsFragment"
private const val REQUEST_LOCATION_PERMISSION = 1

class RestaurantsFragment : BaseFragment(), PermissionCallbacks {

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
        Log.d(TAG, "onCreateView: ")
        // Inflate the layout for this fragment

        binding.rvRestaurants.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = restaurantAdapter
        }
        restaurantViewModel.getRestaurantsByLocation()

        requestLocationPermission();
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

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    fun requestLocationPermission() {
        val perm = (Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(requireContext(), perm)) {
            Log.d(TAG, "requestLocationPermission: Permission already granted")

        } else {
            EasyPermissions.requestPermissions(
                this,
                "Please grant the location permission",
                REQUEST_LOCATION_PERMISSION,
                perm
            )
        }
    }


    override fun onPermissionsGranted(requestCode: Int, list: List<String>) {
        // Some permissions have been granted
        // ...
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:$requestCode" + ":" + perms.size)

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }

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
}
