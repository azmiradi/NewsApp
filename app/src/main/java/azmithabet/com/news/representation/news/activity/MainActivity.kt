package azmithabet.com.news.representation.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import azmithabet.com.news.R
import azmithabet.com.news.databinding.ActivityMainBinding
import azmithabet.com.news.representation.news.adapter.NewsAdapter
import azmithabet.com.news.representation.news.viewmodel.NewsViewModel
import azmithabet.com.news.representation.news.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    @Inject
    lateinit var factory: NewsViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController=navHostFragment.navController

        viewModel= ViewModelProvider(this,factory)[NewsViewModel::class.java]

        binding.bottomNavigation.setupWithNavController(navController)
    }
}