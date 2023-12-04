package com.example.myfoodproject

import CommentRepository
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodproject.databinding.ItemCommentBinding
import com.example.myfoodproject.repository.UserRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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