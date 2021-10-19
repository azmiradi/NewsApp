package azmithabet.com.news.representation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import azmithabet.com.news.R
import azmithabet.com.news.data.model.category.Category
import azmithabet.com.news.databinding.CategoryItemBinding
import com.bumptech.glide.Glide
import java.util.*

class  CategoryAdapter  : RecyclerView.Adapter<CategoryAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.icon == newItem.icon
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = CategoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.bind(category)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(
        private val binding: CategoryItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.name.text = category.name


            Glide.with(binding.icon.context).load(category.icon)
                .placeholder(R.drawable.ic_loading).error(R.drawable.ic_no_image)
                .into(binding.icon)
            binding.root.setOnClickListener {
                onItemClick?.let {
                    it(adapterPosition)
                }
            }
        }


    }

    private var onItemClick:((Int)->Unit)?=null
    fun setOnItemClickListener(listener : (Int)->Unit){
        onItemClick=listener
    }
}