package com.example.myfoodproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.myfoodproject.databinding.FragmentReviewHomeBinding

class ReviewHomeFragment : Fragment(), OnItemClickListener {
    lateinit var binding: FragmentReviewHomeBinding

    val reviewListss = arrayListOf( //DB로 연동받을 부분
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
        Reviewlist(R.drawable.hackbab, R.drawable.male, "일식 매니아", 4.9, "23.11.20"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 뷰를 생성하고 바인딩 초기화
        binding = FragmentReviewHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리사이클러뷰 초기화
        val adapter = ReviewlistAdapter(reviewListss, this)
        binding.rcReviewlist.layoutManager = LinearLayoutManager(requireContext())
        binding.rcReviewlist.adapter = adapter

        // 구분선 만들기
        val decoration = DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
        binding.rcReviewlist.addItemDecoration(decoration)

        binding?.btnLike?.setOnClickListener {
            Toast.makeText(binding.btnLike.context,"좋아요 누른 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 리뷰 작성 화면으로 이동
        binding?.btnWrite2?.setOnClickListener {
            findNavController().navigate(R.id.action_reviewHomeFragment_to_writeFragment)
        }
    }

    override fun onItemClick(reviewlist: Reviewlist) {
        // 클릭된 아이템에 대한 동작 수행
        // 다른 Fragment로 이동하는 코드를 여기에 추가
        findNavController().navigate(R.id.action_reviewHomeFragment_to_reviewDetailFragment)
    }

}

