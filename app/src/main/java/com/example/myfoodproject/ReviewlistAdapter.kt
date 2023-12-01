package com.example.myfoodproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodproject.databinding.FragmentReviewListBinding
interface OnItemClickListener{ // 리사이클러뷰 아이템 클릭을 처리하기 위한 인터페이스 정의
    fun onItemClick(reviewlist: Reviewlist)
}

class ReviewlistAdapter(val reviewlists: ArrayList<Reviewlist>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ReviewlistAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { //xml과 연결
        val binding = FragmentReviewListBinding.inflate(LayoutInflater.from(parent.context)) // Adapter와 연결될 fragment_review_list.xml을 가져옴
        return Holder(binding) // CustomviewHolder의 itemview를 끌어와서 반환
    }


    override fun onBindViewHolder(holder: Holder, position: Int) { // onCreateViewHolder로 만들어진 view를 실질적으로 연결, 스크롤을 할 때나 리스트뷰를 계속 사용할 때 모든 데이터를 안정적으로 매칭
        holder.bind(reviewlists[position])

        holder.itemView.setOnClickListener {
            // 리사이클러뷰의 해당 아이템 데이터를 가져옴
            val selectedReview = reviewlists[position]
            // 인터페이스를 통해 클릭 이벤트 전달
            listener.onItemClick(selectedReview)
        }
    }

    override fun getItemCount() = reviewlists.size //reviewlists의 크기 반환

    //view에 대한 것을 잡아주는 holder역할
    class Holder(private val binding: FragmentReviewListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(reviewlist: Reviewlist) {
            binding.smallPic.setImageResource(R.drawable.hackbab)
            binding.profilePic.setImageResource(R.drawable.male)
            binding.usrName.text = reviewlist.name
            binding.starRate.text = reviewlist.rate.toString()
            binding.date.text = reviewlist.date
        }

    }

}