package com.example.myfoodproject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfoodproject.databinding.ItemReviewBinding
import com.example.myfoodproject.repository.PostRepository



class ReviewlistAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<PostRepository.Post, ReviewlistAdapter.ReviewViewHolder>(PostDiffCallback()) {

    interface OnItemClickListener{
        fun onItemClick(post: PostRepository.Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(post)
        }
    }


    inner class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostRepository.Post) {
            // ViewHolder 뷰에 데이터를 바인딩
            Glide.with(binding.smallPic.context).load(post.imageUrl).into(binding.smallPic)
            binding.usrName.text = post.userId
            binding.starRate.text = post.rating.toString()
            binding.date.text = post.timestamp
        }
    }

}

class PostDiffCallback : DiffUtil.ItemCallback<PostRepository.Post>() {
    override fun areItemsTheSame(oldItem: PostRepository.Post, newItem: PostRepository.Post): Boolean {
        // 항목이 목록에서 동일한 객체를 나타내는지 확인합니다.
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: PostRepository.Post, newItem: PostRepository.Post): Boolean {
        // 항목 내용이 동일한지 확인합니다.
        return oldItem == newItem
    }
}