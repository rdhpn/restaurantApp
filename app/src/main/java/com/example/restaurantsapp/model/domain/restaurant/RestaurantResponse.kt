package com.example.restaurantsapp.model.domain.restaurant


import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("alias")
    val alias: String? = null,
    @SerializedName("categories")
    val categories: List<Category?>? = null,
    @SerializedName("coordinates")
    val coordinates: Coordinates? = null,
    @SerializedName("display_phone")
    val displayPhone: String? = null,
    @SerializedName("hours")
    val hours: List<Hour?>? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("is_claimed")
    val isClaimed: Boolean? = null,
    @SerializedName("is_closed")
    val isClosed: Boolean? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("messaging")
    val messaging: Messaging? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("photos")
    val photos: List<String?>? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("review_count")
    val reviewCount: Int? = null,
    @SerializedName("special_hours")
    val specialHours: List<SpecialHour?>? = null,
    @SerializedName("transactions")
    val transactions: List<String?>? = null,
    @SerializedName("url")
    val url: String? = null
)