package com.example.projekt.data

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("restaurants?name=a")
    fun fetchData(): Call<BEData>
}