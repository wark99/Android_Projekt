package com.example.projekt.data

class RestaurantData(
    private var name: String,
    private var address: String,
    private var image_url: String,
    private var price: String,
    private var favourite: Boolean
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

    fun getImage(): String {
        return image_url
    }

    fun setImage(image_url: String) {
        this.image_url = image_url
    }

    fun getFavourite(): Boolean {
        return favourite
    }

    fun setFavourite(favourite: Boolean) {
        this.favourite = favourite
    }
}