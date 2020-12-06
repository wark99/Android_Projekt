package com.example.projekt.data

class RestaurantsData(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val area: String,
    val postal_code: String,
    val country: String,
    val phone: String,
    val lat: Double,
    val lng: Double,
    val price: Double,
    val reserve_url: String,
    val mobile_reserve_url: String,
    val image_url: String,
    val favourite: Boolean
) {
    override fun toString(): String {
        return "\n" + id.toString() + "\n" + name + "\n" + address + "\n" + city + "\n" + state + "\n" + area + "\n" + postal_code + "\n" + country + "\n" + phone + "\n" + lat.toString() + "\n" + lng.toString() + "\n" + price.toString() + "\n" + reserve_url + "\n" + mobile_reserve_url + "\n" + image_url + "\n" + favourite + "\n\n"
    }
}