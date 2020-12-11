package com.example.projekt.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.projekt.PermissionUtils
import com.example.projekt.R
import com.example.projekt.RestaurantApplication
import com.example.projekt.data.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailScreenFragment : Fragment(), DataListener, OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private val locationPermissionRequestCode = 1
    private val callPermissionRequestCode = 42
    private lateinit var googleMap: GoogleMap
    private var mapPermissionDenied = false
    private var callPermissionDenied = false

    private lateinit var myLocation: Location

    private val restaurantViewModel: RestaurantViewModel by activityViewModels() {
        RestaurantViewModelFactory((requireActivity().application as RestaurantApplication).repository)
    }
    private val dataSet = arrayListOf<RestaurantData>()

    private val singleRestaurantViewModel: SingleRestaurantViewModel by activityViewModels() {
        SingleRestaurantViewModelFactory((requireActivity().application as RestaurantApplication).repository)
    }

    private lateinit var restaurantData: RestaurantData
    private val pickImage = 101

    private lateinit var restaurantName: TextView
    private lateinit var restaurantFavourite: ImageButton
    private lateinit var restaurantImage: ImageView
    private lateinit var restaurantAddress: TextView
    private lateinit var restaurantPrice: TextView
    private lateinit var addImage: Button
    private lateinit var deleteImage: Button
    private lateinit var callButton: Button

    private var imageSet = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_screen, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        restaurantViewModel.allRestaurants.observe(viewLifecycleOwner) { restaurant ->
            restaurant.let {
                dataSet.clear()
                for ((id, element) in it.withIndex()) {
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
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        restaurantName = requireActivity().findViewById(R.id.detailTitleTextView)
        restaurantFavourite = requireActivity().findViewById(R.id.detailFavouriteImageButton)
        restaurantImage = requireActivity().findViewById(R.id.detailImageView)
        restaurantAddress = requireActivity().findViewById(R.id.detailAddressTextView)
        restaurantPrice = requireActivity().findViewById(R.id.detailPriceTextView)
        addImage = requireActivity().findViewById(R.id.addImageButton)
        deleteImage = requireActivity().findViewById(R.id.deleteImageButton)
        callButton = requireActivity().findViewById(R.id.callButton)

        singleRestaurantViewModel.selectedItem.observe(viewLifecycleOwner) { element ->
            restaurantData = RestaurantData(
                element.getId(),
                element.getName(),
                element.getAddress(),
                element.getCity(),
                element.getState(),
                element.getArea(),
                element.getPostalCode(),
                element.getCountry(),
                element.getPhone(),
                element.getLat(),
                element.getLng(),
                element.getPrice(),
                element.getUrl(),
                element.getMobileUrl(),
                element.getImage(),
                element.getFavourite()
            )
            onDataReady()
        }

        restaurantFavourite.setOnClickListener {
            restaurantData.setFavourite(!restaurantData.getFavourite())

            restaurantViewModel.updateFavourite(
                restaurantData.getId(),
                restaurantData.getFavourite(),
                object : DataListener {
                    override fun onDataReady() {
                        if (restaurantData.getFavourite()) {
                            restaurantFavourite.setImageResource(R.drawable.favorite)
                        } else {
                            restaurantFavourite.setImageResource(R.drawable.favorite_border)
                        }
                    }
                })

            /*restaurantViewModel.deleteAll()
            restaurantViewModel.insert(Restaurant(
                restaurantData.getId(),
                restaurantData.getName(),
                restaurantData.getAddress(),
                restaurantData.getCity(),
                restaurantData.getState(),
                restaurantData.getArea(),
                restaurantData.getPostalCode(),
                restaurantData.getCountry(),
                restaurantData.getPhone(),
                restaurantData.getLat(),
                restaurantData.getLng(),
                restaurantData.getPrice().toDouble(),
                restaurantData.getUrl(),
                restaurantData.getMobileUrl(),
                restaurantData.getImage(),
                restaurantData.getFavourite()
            ))
            if (restaurantData.getFavourite()) {
                restaurantFavourite.setImageResource(R.drawable.favorite)
            } else {
                restaurantFavourite.setImageResource(R.drawable.favorite_border)
            }*/
        }

        addImage.setOnClickListener {
            val picture = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(picture, pickImage)
        }
        deleteImage.setOnClickListener {
            dataSet[restaurantData.getId()].setImage("https://www.opentable.com/img/restimages/116272.jpg")
            restaurantViewModel.deleteAll()
            for (element in dataSet) {
                restaurantViewModel.insert(
                    Restaurant(
                        element.getId(),
                        element.getName(),
                        element.getAddress(),
                        element.getCity(),
                        element.getState(),
                        element.getArea(),
                        element.getPostalCode(),
                        element.getCountry(),
                        element.getPhone(),
                        element.getLat(),
                        element.getLng(),
                        element.getPrice().toDouble(),
                        element.getUrl(),
                        element.getMobileUrl(),
                        element.getImage(),
                        element.getFavourite()
                    )
                )
            }
            Glide.with(restaurantImage).load("https://www.opentable.com/img/restimages/116272.jpg")
                .into(restaurantImage)
            imageSet = false
        }
        callButton.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        enableCalling()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        callPhone()
    }

    private fun enableCalling() {
        if (ActivityCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            PermissionUtils.requestPermission(
                activity as AppCompatActivity, callPermissionRequestCode,
                Manifest.permission.CALL_PHONE, false
            )
        }
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + restaurantData.getPhone()))
        startActivity(intent)
    }

    override fun onDataReady() {
        restaurantName.text = restaurantData.getName()
        if (restaurantData.getFavourite()) {
            restaurantFavourite.setImageResource(R.drawable.favorite)
        }
        if (!imageSet) {
            Glide.with(restaurantImage).load(restaurantData.getImage()).into(restaurantImage)
        }
        restaurantAddress.text = restaurantData.getAddress()
        restaurantPrice.text = restaurantData.getPrice()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {

            dataSet[restaurantData.getId()].setImage(data?.data.toString())
            restaurantViewModel.deleteAll()
            for (element in dataSet) {
                restaurantViewModel.insert(
                    Restaurant(
                        element.getId(),
                        element.getName(),
                        element.getAddress(),
                        element.getCity(),
                        element.getState(),
                        element.getArea(),
                        element.getPostalCode(),
                        element.getCountry(),
                        element.getPhone(),
                        element.getLat(),
                        element.getLng(),
                        element.getPrice().toDouble(),
                        element.getUrl(),
                        element.getMobileUrl(),
                        element.getImage(),
                        element.getFavourite()
                    )
                )
            }
            Glide.with(restaurantImage).load(data?.data).into(restaurantImage)
            imageSet = true
        }
    }

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap

        val restaurantCoordinates = LatLng(restaurantData.getLat(), restaurantData.getLng())
        googleMap.addMarker(
            MarkerOptions().position(restaurantCoordinates).title(restaurantData.getName())
        )
        googleMap.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(restaurantCoordinates, 18f), 5000, null
        )

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        googleMap.isMyLocationEnabled = true
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            PermissionUtils.requestPermission(
                activity as AppCompatActivity, locationPermissionRequestCode,
                Manifest.permission.ACCESS_FINE_LOCATION, false
            )
        }

        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true) ?: return
        myLocation = locationManager.getLastKnownLocation(provider)!!
    }

    override fun onMyLocationButtonClick(): Boolean {
        val latitude = myLocation.latitude
        val longitude = myLocation.longitude
        val coordinate = LatLng(latitude, longitude)
        googleMap.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(coordinate, 18f), 5000, null
        )
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(activity, "Current location: $location", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            locationPermissionRequestCode -> {
                if (PermissionUtils.isPermissionGranted(
                        permissions, grantResults,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    // Enable the my location layer if the permission has been granted.
                    enableMyLocation()
                } else {
                    // Permission was denied. Display an error message
                    // Display the missing permission error dialog when the fragments resume.
                    mapPermissionDenied = true
                }
            }
            callPermissionRequestCode -> {
                if (PermissionUtils.isPermissionGranted(
                        permissions, grantResults,
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    // Enable the my location layer if the permission has been granted.
                    enableCalling()
                } else {
                    // Permission was denied. Display an error message
                    // Display the missing permission error dialog when the fragments resume.
                    callPermissionDenied = true
                }
            }
        }
    }
}