package com.example.myfoodproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfoodproject.databinding.ItemReviewBinding
import com.example.myfoodproject.repository.PostRepository
import com.example.myfoodproject.repository.UserRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ReviewlistAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val userRepository: UserRepository
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
            binding.starRate.text = post.rating.toString()
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(post.timestamp))
            binding.date.text = formattedDate

            // 작성자의 UID를 사용하여 UserRepository에서 닉네임 가져오기
            userRepository.observeUser(post.userUid) { user ->
                // user가 null이 아닌 경우에만 닉네임을 설정
                user?.let {
                    binding.usrName.text = it.nick
                }
            }

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