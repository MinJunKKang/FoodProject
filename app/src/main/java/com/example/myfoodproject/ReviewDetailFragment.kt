package com.example.myfoodproject// com.example.myfoodproject.ReviewDetailFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myfoodproject.databinding.FragmentReviewDetailBinding
import com.example.myfoodproject.viewmodel.CommentViewModel
import com.example.myfoodproject.viewmodel.PostViewModel

class ReviewDetailFragment : Fragment() {

    private val postViewModel: PostViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels() // 사실 댓글 뷰모델은 이 fragment에 종속되어 있는거라 viewmodels로 초기화 해도 됨

    private var binding: FragmentReviewDetailBinding? = null

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

        // 전달받은 인자에서 게시물 ID를 가져옵니다.
        val postId = arguments?.getString("postId") ?: ""

        // 선택된 게시물을 가져와서 관찰합니다.
        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            val selectedPost = posts.find { it.postId == postId }
            // UI에 선택된 게시물 내용을 표시합니다.
            binding?.postLayout?.findViewById<TextView>(R.id.textView14)?.text = selectedPost?.content ?: ""
        }
        // 댓글 목록 어댑터 초기화 (예시, 나중에 DB연동)
        val commentAdapter = CommentAdapter()
        binding?.rcComment?.adapter = commentAdapter

        // 댓글 목록 설정
        commentViewModel.getComments()

        // 댓글 목록 관찰
        commentViewModel.comments.observe(viewLifecycleOwner) { comments ->
            commentAdapter.submitList(comments.map { it.commentcontent })
        }

        // 댓글 입력 레이아웃에 대한 이벤트 처리
        binding?.submitCommentButton?.setOnClickListener {
            // 댓글 작성 버튼 클릭 시 처리
            val commentText = binding?.commentEditText?.text.toString()
            if (commentText.isNotEmpty()) {
                // 댓글이 비어 있지 않은 경우에만 추가
                commentAdapter.addComment(commentText)
                binding?.commentEditText?.text?.clear()
            }
        }
        binding?.btnLike?.setOnClickListener {
            Toast.makeText(binding?.btnLike?.context,"좋아요 누른 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
