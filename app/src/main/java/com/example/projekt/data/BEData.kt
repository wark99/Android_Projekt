package com.example.projekt.data

class BEData(
    val totalEntries: Int,
    val perPage: Int,
    val currentPage: Int,
    val restaurants: List<RestaurantsData>
) {
}