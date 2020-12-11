package com.example.projekt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projekt.R
import com.example.projekt.RestaurantApplication
import com.example.projekt.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashScreenFragment : Fragment() {

    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory((requireActivity().application as RestaurantApplication).repository)
    }

    private val profileViewModel: ProfileViewModel by activityViewModels() {
        ProfileViewModelFactory((requireActivity().application as RestaurantApplication).profileRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (profileViewModel.profile.value == null) {
            profileViewModel.insertProfile(
                Profile(
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onResume() {
        super.onResume()

        val retrofit = Retrofit.Builder().baseUrl("https://ratpark-api.imok.space/")
            .addConverterFactory(GsonConverterFactory.create()).build()


        val api = retrofit.create(ApiService::class.java)
        api.fetchData().enqueue(object : Callback<BEData> {
            override fun onResponse(
                call: Call<BEData>,
                response: Response<BEData>
            ) {
                if (restaurantViewModel.allRestaurants.value == null) {
                    for (item in response.body()!!.restaurants) {
                        restaurantViewModel.insert(
                            Restaurant(
                                item.id,
                                item.name,
                                item.address,
                                item.city,
                                item.state,
                                item.area,
                                item.postal_code,
                                item.country,
                                item.phone,
                                item.lat,
                                item.lng,
                                item.price,
                                item.reserve_url,
                                item.mobile_reserve_url,
                                item.image_url,
                                item.favourite
                            )
                        )
                    }
                }
                //restaurantViewModel.deleteAll()

                val navController = findNavController()
                navController.navigate(R.id.action_splashScreenFragment_to_mainScreenFragment)
            }

            override fun onFailure(call: Call<BEData>, t: Throwable) {
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_LONG).show()
                requireActivity().finishAffinity()
            }
        })
    }
}