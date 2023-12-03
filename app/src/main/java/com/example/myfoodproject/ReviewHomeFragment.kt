package com.example.myfoodproject

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodproject.databinding.FragmentReviewHomeBinding
import com.example.myfoodproject.repository.PostRepository
import com.example.myfoodproject.repository.UserRepository
import com.example.myfoodproject.viewmodel.PostViewModel
import com.example.myfoodproject.viewmodel.UserViewModel

class ReviewHomeFragment : Fragment(), ReviewlistAdapter.OnItemClickListener {
    lateinit var binding: FragmentReviewHomeBinding
    private val postViewModel: PostViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val userRepository: UserRepository by lazy { UserRepository() }
    private val adapter: ReviewlistAdapter by lazy { ReviewlistAdapter(this, userRepository) }


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

        binding.rcReviewlist.layoutManager = LinearLayoutManager(context)
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

        // 게시물 관찰을 위한 Observer 추가
        postViewModel.observePosts()
        // Firebasepost를 가져와서 게시물을 UI에 표시하는 Observer
        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            // 게시물 목록이 변경될 때마다 어댑터에 새로운 데이터 설정
            adapter.submitList(posts)
        }
    }

    override fun onItemClick(post: PostRepository.Post) {
        // 클릭된 아이템에 대한 동작 수행
        // 다른 Fragment로 이동하는 코드를 여기에 추가
        val postId = post.postId
        val bundle = Bundle().apply {
            putString("postId", postId) // 클릭된 아이템의 postId를 전달
        }
        findNavController().navigate(R.id.action_reviewHomeFragment_to_reviewDetailFragment, bundle)
    }

}

