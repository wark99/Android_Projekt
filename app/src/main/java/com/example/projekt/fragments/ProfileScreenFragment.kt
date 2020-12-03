package com.example.projekt.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R
import com.example.projekt.RecyclerViewFavouriteAdapter

class ProfileScreenFragment : Fragment() {

    private val dataSet = arrayListOf<String>()
    private lateinit var adapter: RecyclerViewFavouriteAdapter

    private lateinit var profilePicture: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataSet.add("Alma")
        dataSet.add("Korte")
        dataSet.add("Szilva")
        dataSet.add("Palinka")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_screen, container, false)
    }

    override fun onResume() {
        super.onResume()

        profilePicture = activity?.findViewById(R.id.avatarImageView)!!

        adapter = RecyclerViewFavouriteAdapter(dataSet)
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.favouritesRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        profilePicture.setOnClickListener {
            val picture = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(picture, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            profilePicture.setImageURI(imageUri)
        }
    }
}