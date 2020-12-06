package com.example.projekt.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt.R
import com.example.projekt.RecyclerViewFavouriteAdapter
import com.example.projekt.RestaurantApplication
import com.example.projekt.data.*

class ProfileScreenFragment : Fragment(), DataListener, ProfileListener,
    RecyclerViewFavouriteAdapter.OnFavouriteClickListener {

    private val profileViewModel: ProfileViewModel by activityViewModels() {
        ProfileViewModelFactory((requireActivity().application as RestaurantApplication).profileRepository)
    }

    private lateinit var profile: Profile

    private val restaurantViewModel: RestaurantViewModel by activityViewModels() {
        RestaurantViewModelFactory((requireActivity().application as RestaurantApplication).repository)
    }

    private val dataSet = arrayListOf<RestaurantData>()
    private lateinit var adapter: RecyclerViewFavouriteAdapter

    private lateinit var profilePicture: ImageView
    private lateinit var profileName: EditText
    private lateinit var profileAddress: EditText
    private lateinit var profilePhone: EditText
    private lateinit var profileMail: EditText
    private lateinit var saveProfile: Button

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_screen, container, false)

        getData()

        return view
    }

    override fun onResume() {
        super.onResume()

        profilePicture = requireActivity().findViewById(R.id.avatarImageView)
        profileName = requireActivity().findViewById(R.id.nameInputEditText)
        profileAddress = requireActivity().findViewById(R.id.addressInputEditText)
        profilePhone = requireActivity().findViewById(R.id.phoneNumberEditText)
        profileMail = requireActivity().findViewById(R.id.mailInputEditText)
        saveProfile = requireActivity().findViewById(R.id.saveButton)

        profileViewModel.profile.observe(viewLifecycleOwner) { element ->
            element.let {
                profile = it
                onProfileReady()
            }
        }

        profilePicture.setOnClickListener {
            val picture = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(picture, pickImage)
        }

        saveProfile.setOnClickListener {
            profileViewModel.deleteProfile()
            profileViewModel.insertProfile(
                Profile(
                    profilePicture.toString(),
                    profileName.text.toString(),
                    profileAddress.text.toString(),
                    profilePhone.text.toString(),
                    profileMail.text.toString()
                )
            )
            val navController = findNavController()
            navController.navigate(R.id.action_profileScreenFragment_to_mainScreenFragment)
        }
    }

    private fun getData() {
        restaurantViewModel.allRestaurants.observe(viewLifecycleOwner) { restaurant ->
            restaurant.let {
                for ((id, element) in it.withIndex()) {
                    if (element.favourite) {
                        dataSet.add(
                            RestaurantData(
                                id,
                                element.name,
                                element.address,
                                element.city,
                                element.state,
                                element.area,
                                element.postal_code,
                                element.country,
                                element.phone,
                                element.lat,
                                element.lng,
                                element.price.toString(),
                                element.reserve_url,
                                element.mobile_reserve_url,
                                element.image_url,
                                element.favourite
                            )
                        )
                    }
                }
                onDataReady()
            }
        }
    }

    override fun onDataReady() {
        adapter = RecyclerViewFavouriteAdapter(dataSet, this)
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.favouritesRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }

    override fun onProfileReady() {
        if (profile.avatar.isNotEmpty()) {
            Glide.with(profilePicture).load(profile.avatar).into(profilePicture)
        }
        profileName.setText(profile.name)
        profileAddress.setText(profile.address)
        profilePhone.setText(profile.phone)
        profileMail.setText(profile.mail)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            profilePicture.setImageURI(imageUri)
        }
    }

    override fun onFavouriteClick(position: Int) {
        dataSet.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}