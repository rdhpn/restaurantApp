package com.example.restaurantsapp.view

import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.utils.BaseFragment
import com.example.restaurantsapp.utils.UIState
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsapp.databinding.FragmentDetailsBinding
import com.example.restaurantsapp.model.domain.ReviewDomain
import com.example.restaurantsapp.utils.ViewType
import com.example.restaurantsapp.view.adapter.RestaurantAdapter


class DetailsFragment : BaseFragment() {


    private val restaurantAdapter by lazy {
        RestaurantAdapter {}
    }

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvReview.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = restaurantAdapter

        }

        val id = restaurantViewModel.id

        restaurantViewModel.getRestaurantById(id)

        restaurantViewModel.restaurantById.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS<RestaurantDomain> -> {
                    binding.tvName.text = state.response.name
                    binding.tvPrice.text = "Price: " + state.response.price
                    binding.tvImageUrl.text = state.response.image ?: "NO NAME PROVIDED"
                    binding.tvRating.rating = state.response.rating.toFloat()
//                        "Overall Rating: " + (state.response.rating ?: 0.0).toString()
                    binding.tvPhone.text = state.response.phone
                    binding.tvAddress.text = state.response.address
                }
                is UIState.ERROR -> {
                    state.error.localizedMessage?.let {
                        showError(it) {
                        }
                    }
                }
            }
        }

        restaurantViewModel.getReviewsById(id)
        restaurantViewModel.reviewsById.observe(viewLifecycleOwner) { state ->
            val listVT: MutableList<ViewType> = mutableListOf()

            when (state) {
                is UIState.LOADING -> {

                }
                is UIState.SUCCESS<List<ReviewDomain>> -> {
                    state.response.forEach {
                        listVT.add(ViewType.REVIEW(it))
                    }
                    restaurantAdapter.updateItems(listVT)
                }

                is UIState.ERROR -> {
                    state.error.localizedMessage?.let {
                        showError(it) {
                        }
                    }
                }
            }
        }

        return binding.root
    }

}
