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

        val postRepository = PostRepository()

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

            // 평균 평점 계산 및 표시
            if (posts.isNotEmpty()) {
                // PostRepository의 calculateAverageRating 함수에서는 Double을 반환하므로 TextView에 설정하기 전에 String으로 변환 필요
                postRepository.calculateAverageRating(posts) { averageRating ->
                    val formattedRating = String.format("%.2f", averageRating)

                    // 평균 평점을 textView6에 설정
                    binding.textView6.text = formattedRating

                    // 게시물 개수를 업데이트
                    postViewModel.loadPostsCount()
                }
                // 수정 종료
            } else {
                // 게시물이 없는 경우 평균 평점을 0.0으로 설정
                binding.textView6.text = "0.00"
                // 게시물 개수를 업데이트
                postViewModel.loadPostsCount()
            }
        }
        // 게시물 개수를 UI에 표시하는 Observer
        postViewModel.postCount.observe(viewLifecycleOwner) { postCount ->
            // 게시물 총 개수를 UI에 표시
            binding.textView9.text = postCount.toString()
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

