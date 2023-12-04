package com.example.myfoodproject// com.example.myfoodproject.ReviewDetailFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myfoodproject.databinding.FragmentReviewDetailBinding
import com.example.myfoodproject.repository.PostRepository
import com.example.myfoodproject.repository.UserRepository
import com.example.myfoodproject.viewmodel.CommentViewModel
import com.example.myfoodproject.viewmodel.PostViewModel
import com.example.myfoodproject.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewDetailFragment : Fragment() {

    private val postViewModel: PostViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels() // 사실 댓글 뷰모델은 이 fragment에 종속되어 있는거라 viewmodels로 초기화 해도 됨

    private var binding: FragmentReviewDetailBinding? = null

    private val postRepository: PostRepository by lazy { PostRepository() }
    private val userRepository: UserRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰바인딩 초기화
        binding = FragmentReviewDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달받은 인자에서 게시물 ID를 가져옴
        val postId = arguments?.getString("postId") ?: ""

        // 선택된 게시물을 가져와서 관찰
        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            val selectedPost = posts.find { it.postId == postId }
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedPost!!.timestamp))
            // UI에 선택된 게시물 내용을 표시
            binding?.postLayout?.findViewById<TextView>(R.id.textView14)?.text = selectedPost?.content ?: ""
            binding?.postLayout?.findViewById<TextView>(R.id.rv_detalitile)?.text = selectedPost?.title?: ""
            binding?.postLayout?.findViewById<TextView>(R.id.textView23)?.text = formattedDate
            // 작성자의 UID를 사용하여 UserRepository에서 닉네임 가져오기
            userRepository.observeUser(selectedPost!!.userUid) { user ->
                // user가 null이 아닌 경우에만 닉네임을 설정
                user?.let {
                    binding?.postLayout?.findViewById<TextView>(R.id.textView22)?.text = it.nick
                }
            }
            val imageView = binding?.postLayout?.findViewById<ImageView>(R.id.review_pic)
            val imageUrl = selectedPost?.imageUrl

            if (imageUrl != null) {
                // imageUrl이 null이 아닌 경우 Glide를 사용하여 이미지를 표시
                Glide.with(requireContext())
                    .load(imageUrl)
                    .into(imageView!!)
            } else {
                // imageUrl이 null인 경우, 이미지 미표시
                imageView?.setImageResource(0)
            }

            // 평균 평점 계산 및 표시
            if (posts.isNotEmpty()) {
                // TextView에 설정하기 전에 String으로 변환 필요
                postRepository.calculateAverageRating(posts) { averageRating ->
                    val formattedRating = String.format("%.2f", averageRating)

                    // 평균 평점을 textView6에 설정
                    binding?.textView6?.text = formattedRating

                    // 게시물 개수를 업데이트
                    postViewModel.loadPostsCount()
                }
                // 수정 종료
            } else {
                // 게시물이 없는 경우 평균 평점을 0.0으로 설정
                binding?.textView6?.text = "0.00"
                // 게시물 개수를 업데이트
                postViewModel.loadPostsCount()
            }
        }

        // 게시물 개수를 UI에 표시하는 Observer
        postViewModel.postCount.observe(viewLifecycleOwner) { postCount ->
            // 게시물 총 개수를 UI에 표시
            binding?.textView9?.text = postCount.toString()
        }

        //게시물 삭제
        binding?.btnDelete?.setOnClickListener {
            postViewModel.deletePost(postId) { success, message ->
                if( success ) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
            findNavController().navigate(R.id.action_reviewDetailFragment_to_reviewHomeFragment)
        }


        // 댓글 목록 어댑터 초기화
        val commentAdapter = CommentAdapter(commentViewModel.commentRepository, userViewModel.userRepository)
        binding?.rcComment?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rcComment?.adapter = commentAdapter

        // 댓글 목록 설정
        commentViewModel.getComments(postId)

        // 댓글 목록 관찰
        commentViewModel.comments.observe(viewLifecycleOwner) { comments ->
            commentAdapter.submitList(comments)

        }


        // 댓글 입력 레이아웃에 대한 이벤트 처리
        binding?.submitCommentButton?.setOnClickListener {
            // 댓글 작성 버튼 클릭 시 처리
            val commentText = binding?.commentEditText?.text.toString()
            if (commentText.isNotEmpty()) {

                // 댓글이 비어 있지 않은 경우에만 추가
                commentViewModel.addComment(postId, commentText){ success, message ->
                    if (success){
                        Toast.makeText(requireContext(), "댓글이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireContext(), "댓글 추가에 실패했습니다. $message", Toast.LENGTH_SHORT).show()
                    }
                }
                binding?.commentEditText?.text?.clear()
            }
        }
        binding?.btnLike?.setOnClickListener {
            Toast.makeText(binding?.btnLike?.context,"좋아요 누른 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 댓글 구분선 만들기
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding?.rcComment?.addItemDecoration(decoration)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


