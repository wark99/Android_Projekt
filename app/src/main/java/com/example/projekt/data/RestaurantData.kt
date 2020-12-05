package com.example.projekt.data

class RestaurantData(
    private var id: Int,
    private var name: String,
    private var address: String,
    private var city: String,
    private var state: String,
    private var area: String,
    private var postal_code: String,
    private var country: String,
    private var phone: String,
    private var lat: Double,
    private var lng: Double,
    private var price: String,
    private var reserve_url: String,
    private var mobile_reserve_url: String,
    private var image_url: String,
    private var favourite: Boolean
) {
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

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

    fun getCity(): String {
        return city
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun getState(): String {
        return state
    }

    fun setState(state: String) {
        this.state = state
    }

    fun getArea(): String {
        return area
    }

    fun setArea(area: String) {
        this.area = area
    }

    fun getPostalCode(): String {
        return postal_code
    }

    fun setPostalCode(postal_code: String) {
        this.postal_code = postal_code
    }

    fun getCountry(): String {
        return country
    }

    fun setCountry(country: String) {
        this.country = country
    }

    fun getPhone(): String {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getLat(): Double {
        return lat
    }

    fun setLat(lat: Double) {
        this.lat = lat
    }

    fun getLng(): Double {
        return lng
    }

    fun setLng(lng: Double) {
        this.lng = lng
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
    }

    fun getUrl(): String {
        return reserve_url
    }

    fun setUrl(reserve_url: String) {
        this.reserve_url = reserve_url
    }

    fun getMobileUrl(): String {
        return mobile_reserve_url
    }

    fun setMobileUl(mobile_reserve_url: String) {
        this.mobile_reserve_url = mobile_reserve_url
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