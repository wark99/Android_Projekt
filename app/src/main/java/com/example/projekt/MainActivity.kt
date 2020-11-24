package com.example.projekt

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val dataSet = arrayListOf<RestaurantData>()
    private lateinit var adapter: RecyclerViewFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main_screen)

        dataSet.add(RestaurantData("alma", "asd", "12"))
        dataSet.add(RestaurantData("korte", "qwe", "121"))
        dataSet.add(RestaurantData("szilva", "zxc", "122"))
        dataSet.add(RestaurantData("barack", "xyz", "123"))
        dataSet.add(RestaurantData("palinka", "sad", "124"))

        adapter = RecyclerViewFeedAdapter(dataSet)
        val recyclerView = findViewById<RecyclerView>(R.id.feedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
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
        return true
    }
}