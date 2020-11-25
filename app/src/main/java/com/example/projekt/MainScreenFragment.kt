package com.example.projekt

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MainScreenFragment : Fragment(), RecyclerViewFeedAdapter.OnItemClickListener,
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    private val dataSet = arrayListOf<RestaurantData>()
    private lateinit var adapter: RecyclerViewFeedAdapter

    private val locationPermissionRequestCode = 1
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onResume() {
        super.onResume()

        dataSet.add(RestaurantData("alma", "asd", "12"))
        dataSet.add(RestaurantData("korte", "qwe", "121"))
        dataSet.add(RestaurantData("szilva", "zxc", "122"))
        dataSet.add(RestaurantData("barack", "xyz", "123"))
        dataSet.add(RestaurantData("palinka", "sad", "124"))

        adapter = RecyclerViewFeedAdapter(dataSet, this)
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.feedRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        val mapFragment = parentFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)
        val search: MenuItem = menu.findItem(R.id.searchButton)
        val searchView: SearchView = search.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onItemClick(position: Int) {
        val navController = findNavController()
        navController.navigate(R.id.action_mainScreenFragment_to_detailScreenFragment)
    }

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        googleMap.isMyLocationEnabled = true
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)

        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true) ?: return
        val location = locationManager.getLastKnownLocation(provider)

        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            val coordinate = LatLng(latitude, longitude)
            googleMap.animateCamera(
                CameraUpdateFactory
                    .newLatLngZoom(coordinate, 18f), 5000, null
            )
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionRequestCode
            )
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(activity, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(activity, "Current location: $location", Toast.LENGTH_LONG).show()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }
}