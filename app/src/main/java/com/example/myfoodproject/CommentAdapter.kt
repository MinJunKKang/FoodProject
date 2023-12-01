package com.example.myfoodproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter() : ListAdapter<String, CommentAdapter.CommentViewHolder>(CommentDiffCallback()) {

    // 뷰홀더 객체 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_review_detail, parent, false)
        return CommentViewHolder(view)
    }

    // 뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 새로운 댓글 추가
    fun addComment(comment: String) {
        submitList(currentList.toMutableList().apply { add(comment) })
    }

    //효율적으로 댓글 업데이트
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentTextView: TextView = itemView.findViewById(R.id.rc_comment)

        fun bind(comment: String) {
            commentTextView.text = comment
        }
    }

    private class CommentDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}