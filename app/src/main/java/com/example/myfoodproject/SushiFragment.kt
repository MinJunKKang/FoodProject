package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodproject.databinding.FragmentSushiBinding

class SushiFragment : Fragment() {

    val restaurants = arrayOf(
        Restaurant("스시","스시 월드", 4.2, 566),
        Restaurant("스시","장인 스시", 4.7, 1024),
    )

    var binding: FragmentSushiBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSushiBinding.inflate(inflater)

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

