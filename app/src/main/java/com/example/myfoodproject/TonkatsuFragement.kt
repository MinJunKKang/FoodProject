package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodproject.databinding.FragmentTonkatsuBinding

class TonkatsuFragement : Fragment() {

    val restaurants = arrayOf(
        Restaurant("돈카츠의 신", 4.9, 1254),
        Restaurant("최고 돈카츠", 4.5, 841),
        Restaurant("돈카츠 월드", 4.4, 946),
        Restaurant("돈카츠 나라", 4.1, 1024),
        Restaurant("맛있는 돈카츠", 4.6, 948),
        Restaurant("전통 돈카츠", 4.8, 885),
        Restaurant("장인 돈카츠", 4.0, 1350),
        Restaurant("돈카츠 천국", 4.8, 998),
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

        binding?.btnMove3?.setOnClickListener{
            findNavController().navigate(R.id.action_tonkatsuFragement_to_reviewHomeFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

