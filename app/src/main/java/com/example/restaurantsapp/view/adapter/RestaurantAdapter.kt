package com.example.restaurantsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsapp.R
import com.example.restaurantsapp.databinding.RestaurantViewHolderBinding
import com.example.restaurantsapp.databinding.ReviewViewHolderBinding
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.ReviewDomain
import com.example.restaurantsapp.utils.ViewType

class RestaurantAdapter(
    private val itemSet: MutableList<ViewType> = mutableListOf(),
    private val onItemClick: (RestaurantDomain) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateItems(newItems: List<ViewType>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            RestaurantViewHolder(
                RestaurantViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ReviewViewHolder(
                ReviewViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemSet[position]) {
            is ViewType.RESTAURANT -> 0
            is ViewType.REVIEW -> 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = itemSet[position]) {
            is ViewType.RESTAURANT -> (holder as RestaurantViewHolder).bind(
                item.restaurant,
                onItemClick
            )
            is ViewType.REVIEW -> (holder as ReviewViewHolder).bind(
                item.review
            )
        }
    }

    override fun getItemCount(): Int = itemSet.size
}

class RestaurantViewHolder(
    private val binding: RestaurantViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RestaurantDomain, onItemClick: (RestaurantDomain) -> Unit) {
        binding.tvName.text = item.name ?: "NO NAME PROVIDED"
        binding.tvPrice.text = "Price: " + item.price ?: "NO NAME PROVIDED"
        binding.tvImageUrl.text = item.image ?: "NO NAME PROVIDED"
        binding.tvRating.text = "Rating: " + (item.rating ?: 0.0).toString()
        binding.tvDistance.text = "Distance: " + (item.distance ?: 0.0).toString()
        itemView.setOnClickListener { item?.let(onItemClick) }
    }
}
class ReviewViewHolder(
    private val binding: ReviewViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReviewDomain) {
        binding.tvUserName.text = item.name ?: "NO NAME PROVIDED"
        binding.tvUserRating.rating = item.userRating!!.toFloat()
//            "Her/His Rating: " + (item.userRating ?: 0.0).toString()
        binding.tvDateOfReview.text = item.dayOfReview ?: "NO NAME PROVIDED"
        binding.tvReviewContent.text = item.reviewContent ?: "NO NAME PROVIDED"
        Glide
            .with(binding.root)
            .load(item.userIconUrl)
            .centerCrop()
            .placeholder(R.drawable.baseline_image_search_24)
            .error(R.drawable.baseline_broken_image_24)
            .into(binding.tvIcon)
    }
}
