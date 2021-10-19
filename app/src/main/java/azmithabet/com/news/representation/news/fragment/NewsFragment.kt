package azmithabet.com.news.representation.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import azmithabet.com.news.R
import azmithabet.com.news.data.model.category.Category
import azmithabet.com.news.databinding.FragmentNewsBinding
import azmithabet.com.news.representation.news.activity.MainActivity
import azmithabet.com.news.representation.news.adapter.CategoryAdapter
import azmithabet.com.news.representation.news.adapter.NewsAdapter
import azmithabet.com.news.representation.news.viewmodel.NewsViewModel
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment()  {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel

    private lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    private val country: String = "eg"
    private var category: String = "general"
    private var querySearch: String = ""

    private var page: Int = 1
    private var pages: Int = 0

    private var isScrolling: Boolean = false
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false

    private var isSearch: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter=(activity as MainActivity).newsAdapter

        initRecyclerView()
        initCategoryRecyclerView()
        viewModelNews()
        viewModelSearchNews()

        initSearchView()

    }

    private fun initCategoryRecyclerView() {

        binding.categoryRecycler.apply {
             adapter = categoryAdapter
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }

        categoryAdapter.setOnItemClickListener {
             binding.categoryRecycler.smoothScrollToPosition(it)
        }

        val list = ArrayList<Category>()
        list.add(Category("business",R.drawable.ic_business, 0,false))
        list.add(Category("entertainment",R.drawable.ic_en, 1,false))
        list.add(Category("general",R.drawable.ic_general, 2,false))
        list.add(Category("health",R.drawable.ic_health, 3,false))
        list.add(Category("science",R.drawable.ic__science, 4,false))
        list.add(Category("sports",R.drawable.ic_sports, 5,false))
        list.add(Category("technology",R.drawable.ic_technology, 6,false))


        categoryAdapter.differ.submitList(list)


        binding.categoryRecycler.setOrientation(DSVOrientation.HORIZONTAL); //Sets an orientation of the view
        binding.categoryRecycler.setOffscreenItems(0); //Reserve extra space equal to (childSize * count) on each side of the view
        binding.categoryRecycler.setOverScrollEnabled(true);
        binding.categoryRecycler.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )
       binding.categoryRecycler.addOnItemChangedListener { viewHolder, adapterPosition ->
           run {
               isSearch = false
               MainScope().launch {
                   delay(1000)
                   page=1
                   if (category!=list[adapterPosition].name)
                   {
                       category = list[adapterPosition].name
                       viewModel.getNews(country, page, category)
                   }
               }
           }
       }
        binding.categoryRecycler.scrollToPosition(2)

    }


    private fun initSearchView() {
        binding.searchView.setOnCloseListener {
            isSearch = false
            viewModelNews()
           false
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (querySearch != query.toString()) {
                    page=1
                    querySearch = query.toString()
                    viewModel.getSearchNews(querySearch, country, page, category)
                    isSearch = true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("Query Text: "+ newText.toString())
                MainScope().launch {
                    delay(2000)
                    if (querySearch != newText.toString()) {
                        page=1
                        querySearch = newText.toString()
                        viewModel.getSearchNews(querySearch, country, page, category)
                        isSearch = true
                    }

                }
                return false
            }

        })

    }

    private fun initRecyclerView() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_item", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailsFragment, bundle
            )
        }
    }

    private fun viewModelNews() {
        viewModel.apply {
            getNews(country, page, category)

            newsStream.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is azmithabet.com.news.data.util.Resource.Success -> {
                        hideLoadingBar()
                        response.data.let {
                            newsAdapter.differ.submitList(it!!.articles!!.toList())
                            pages = if (it.totalResults!! % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }
                            isLastPage = pages == page
                        }
                    }
                    is azmithabet.com.news.data.util.Resource.Error -> {
                        hideLoadingBar()
                    }
                    is azmithabet.com.news.data.util.Resource.Loading -> {
                        showLoadingBar()
                    }

                }
            })
        }
    }

    private fun viewModelSearchNews() {
        viewModel.apply {
            searchNewsStream.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is azmithabet.com.news.data.util.Resource.Success -> {
                        hideLoadingBar()
                        response.data.let {
                            newsAdapter.differ.submitList(it!!.articles!!.toList())
                            pages = if (it.totalResults!! % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }
                            isLastPage = pages == page
                        }
                    }
                    is azmithabet.com.news.data.util.Resource.Error -> {
                        hideLoadingBar()
                    }
                    is azmithabet.com.news.data.util.Resource.Loading -> {
                        showLoadingBar()
                    }

                }
            })
        }
    }

    private fun showLoadingBar() {
        isLoading = true
        binding.loadingBar.visibility = VISIBLE

    }

    private fun hideLoadingBar() {
        isLoading = false
        binding.loadingBar.visibility = GONE


    }


    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManger = binding.recycler.layoutManager as LinearLayoutManager
            val currentSize = layoutManger.itemCount
            val visibleItem = layoutManger.childCount
            val topPosition = layoutManger.findFirstVisibleItemPosition()

            val haseReachedToEnd = topPosition + visibleItem >= currentSize

            val shouldPaginate = !isLoading && !isLastPage && haseReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                if (isSearch)
                    viewModel.getSearchNews(querySearch, country, page, category)
                else
                    viewModel.getNews(country, page, category)

                isScrolling = false
            }
        }
    }


}