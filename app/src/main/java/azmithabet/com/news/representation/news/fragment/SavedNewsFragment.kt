package azmithabet.com.news.representation.news.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import azmithabet.com.news.R
import azmithabet.com.news.databinding.FragmentSavedNewsBinding
import azmithabet.com.news.representation.news.activity.MainActivity
import azmithabet.com.news.representation.news.adapter.NewsAdapter
import azmithabet.com.news.representation.news.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter=(activity as MainActivity).newsAdapter

        initRecyclerView()
        viewModelNews()


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                 viewModel.deleteArticle(article)
                    .observe(requireActivity(),{
                        if (it!=0)
                        {
                            Snackbar.make(view,R.string.article_deleted,Snackbar.LENGTH_LONG)
                                .apply {
                                    setAction(R.string.undo){
                                        viewModel.saveArticle(article)
                                            .observe(requireActivity(),{

                                            })
                                    }
                                    show()
                                }
                        }
                    })
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recycler)
        }
    }

    private fun viewModelNews() {
        viewModel.getSavedArticles().observe(requireActivity(),{
            newsAdapter.differ.submitList(it)
        })
    }

    private fun initRecyclerView() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
         }
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_item", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_newsDetailsFragment, bundle
            )
        }
    }
}