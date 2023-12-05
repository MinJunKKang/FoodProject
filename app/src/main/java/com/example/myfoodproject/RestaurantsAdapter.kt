package com.example.myfoodproject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodproject.databinding.ListRestaurantsBinding
class   RestaurantsAdapter(val restaurants: Array<Restaurant>)
    : RecyclerView.Adapter<RestaurantsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder    {
        val binding = ListRestaurantsBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(restaurants[position])
    }
    override fun getItemCount() = restaurants.size

    class Holder(private val binding: ListRestaurantsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(restaurant: Restaurant){
            binding.imageView4.setImageResource(R.drawable.star)
            binding.txtName.text = restaurant.name
            binding.txtRate.text = restaurant.rate.toString()
            binding.txtReview.text = restaurant.review.toString()


            binding.root.setOnClickListener{
                Toast.makeText(binding.root.context, " ${restaurant.name} 리뷰 목록에 접속하셨습니다.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}