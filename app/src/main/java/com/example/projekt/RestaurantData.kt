package com.example.projekt

data class RestaurantData(
    private var name: String,
    private var address: String,
    private var price: String
) {
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAddress(): String {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
    }
}
