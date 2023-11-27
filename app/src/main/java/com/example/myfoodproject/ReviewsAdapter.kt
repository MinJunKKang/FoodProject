package com.example.myfoodproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodproject.databinding.ListReviewsBinding
class ReviewsAdapter(val reviews: Array<Review>): RecyclerView.Adapter<ReviewsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapter.Holder {
        val binding = ListReviewsBinding.inflate(LayoutInflater.from(parent.context))
        return ReviewsAdapter.Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size

    class Holder(private val binding: ListReviewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review: Review){

            binding.txtComment.text = review.comment
            binding.txtGrade.text = review.grade.toString()
            binding.txtDate.text = review.date.toString()
        }
    }
}