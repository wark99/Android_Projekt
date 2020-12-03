package com.example.projekt.fragments

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R
import com.example.projekt.RecyclerViewFeedAdapter
import com.example.projekt.RestaurantApplication
import com.example.projekt.data.*

class MainScreenFragment : Fragment(), RecyclerViewFeedAdapter.OnItemClickListener, DataListener {

    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory((requireActivity().application as RestaurantApplication).repository)
    }

    private lateinit var navController: NavController

    private val dataSet = arrayListOf<RestaurantData>()
    private lateinit var adapter: RecyclerViewFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        restaurantViewModel.allRestaurants.observe(viewLifecycleOwner) { restaurant ->
            restaurant.let {
                for (element in it) {
                    dataSet.add(
                        RestaurantData(
                            element.name,
                            element.address,
                            element.image_url,
                            element.price.toString(),
                            false
                        )
                    )
                }
                onDataReady()
            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onDataReady() {
        adapter = RecyclerViewFeedAdapter(dataSet, this)
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.feedRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        navController = findNavController()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val profile: MenuItem = menu.findItem(R.id.profileButton)
        profile.setOnMenuItemClickListener {
            navController.navigate(R.id.action_mainScreenFragment_to_profileScreenFragment)
            return@setOnMenuItemClickListener true
        }

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
}