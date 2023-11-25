package com.example.foodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodproject.databinding.FragmentPersonalreviewBinding

class PersonalReviewFragment : Fragment() {
    val reviews = arrayOf(
        Review("맛있습니다.",4.0, "23.11.13"),
    )

    var binding: FragmentPersonalreviewBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalreviewBinding.inflate(inflater)

        binding?.recReviews?.layoutManager = LinearLayoutManager(context)
        binding?.recReviews?.adapter = ReviewsAdapter(reviews)


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





