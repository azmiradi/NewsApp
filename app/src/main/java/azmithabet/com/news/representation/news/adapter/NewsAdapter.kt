package azmithabet.com.news.representation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import azmithabet.com.news.R
import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.databinding.NewsItemBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class  NewsAdapter  : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    var oldeFormate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val formatter: SimpleDateFormat =
        SimpleDateFormat("E, dd MMM yyyy HH:mm", Locale("ar", "eg"))
    private val callback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(
        val binding: NewsItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.title.text = article.title

            var publishTime: String = article.publishedAt.toString()
            try {
                val date = oldeFormate.parse(publishTime.replace("Z","").replace("T"," "))
                publishTime=formatter.format(date)
            } catch (e: Exception) {
                println("Exception Formatter: " + e.message)
            }
            binding.time.text = publishTime

            Glide.with(binding.articlePic.context).load(article.urlToImage)
                .placeholder(R.drawable.ic_loading).error(R.drawable.ic_no_image)
                .into(binding.articlePic)
            binding.root.setOnClickListener {
                onItemClick?.let {
                    it(article)
                }
            }
        }


    }

    private var onItemClick:((ArticlesItem)->Unit)?=null
    fun setOnItemClickListener(listener : (ArticlesItem)->Unit){
        onItemClick=listener
    }
}