package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodproject.databinding.FragmentTonkatsuBinding

class TonkatsuFragement : Fragment() {

    val restaurants = arrayOf(
        Restaurant("돈카츠","맛있는 돈카츠", 4.1, 497),
        Restaurant("돈카츠","돈카츠 천국", 4.4, 774),
    )

    var binding: FragmentTonkatsuBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTonkatsuBinding.inflate(inflater)

        binding?.recRestaurants?.layoutManager = LinearLayoutManager(context)
        binding?.recRestaurants?.adapter = RestaurantsAdapter(restaurants)


        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

