package azmithabet.com.news.representation.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import azmithabet.com.news.R
import azmithabet.com.news.databinding.FragmentNewsDetailsBinding
import azmithabet.com.news.representation.news.activity.MainActivity
import azmithabet.com.news.representation.news.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsDetailsBinding.bind(view)

        val args: NewsDetailsFragmentArgs by navArgs()
        val articlesItem = args.selectedItem

        setupWebView()
        if (articlesItem.url != null) {
            binding.webView.loadUrl(articlesItem.url)
        }

        viewModel = (activity as MainActivity).viewModel

        binding.save.setOnClickListener {
            viewModel.saveArticle(articlesItem).observe(this, {
                if (it.toInt() != -1) {
                    Snackbar.make(requireContext(),view, getString(R.string.article_saved), Snackbar.LENGTH_LONG)
                        .show()
                }
            })

        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, progress: Int) {

                    if (progress == 100) {
                        binding.progressBar.visibility = GONE
                    }
                }
            }
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }


    }
}