package com.example.myfoodproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodproject.databinding.FragmentPersonalreviewBinding

class PersonalReviewFragment : Fragment() {
    val reviews = arrayOf(
        Review("너무 맛있게 잘 먹었습니다! 인테리어도 깔끔하고 다음에 또 올께요~ 메뉴가 다양해서 좋았습니다! 다른 분들도 여기 오는 거 추천합니다~"
            ,4.0, "23.11.13"),
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