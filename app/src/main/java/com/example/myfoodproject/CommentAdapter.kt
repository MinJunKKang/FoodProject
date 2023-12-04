package com.example.myfoodproject

import CommentRepository
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodproject.databinding.ItemCommentBinding
import com.example.myfoodproject.repository.UserRepository

class CommentAdapter(private val commentRepository: CommentRepository, private val userRepository: UserRepository) :
    ListAdapter<CommentRepository.Comment, CommentAdapter.CommentViewHolder>(CommentDiffCallback()) {


    // 뷰홀더 객체 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    // 뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    //효율적으로 댓글 업데이트
    inner class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentRepository.Comment) {
            binding.textView13.text = comment.commentcontent
            val formattedDate = comment.commenttimestamp
            binding.date.text = formattedDate
            userRepository.observeUser(comment.userId) { user ->
                // user가 null이 아닌 경우에만 닉네임을 설정
                user?.let {
                    binding.usrName.text = it.nick
                }
            }
            // 댓글 삭제 버튼 클릭 이벤트 처리
            binding.btnCommentdelete.setOnClickListener {
                deleteComment(comment)
            }
        }
        // 댓글 삭제 함수
        private fun deleteComment(comment: CommentRepository.Comment) {
            commentRepository.deleteComment(comment.commentId) { success, message ->
                if (success) {
                    Toast.makeText(itemView.context, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(itemView.context, "댓글 삭제에 실패했습니다. $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private class CommentDiffCallback : DiffUtil.ItemCallback<CommentRepository.Comment>() {
        override fun areItemsTheSame(oldItem: CommentRepository.Comment, newItem: CommentRepository.Comment): Boolean {
            return oldItem.commentId == newItem.commentId
        }

        override fun areContentsTheSame(oldItem: CommentRepository.Comment, newItem: CommentRepository.Comment): Boolean {
            return oldItem == newItem
        }
    }
}