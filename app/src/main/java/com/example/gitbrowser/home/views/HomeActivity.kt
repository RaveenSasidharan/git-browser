package com.example.gitbrowser.home.views


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitbrowser.GitBrowserApplication
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.ActivityHomeBinding
import com.example.gitbrowser.home.home.di.DaggerHomeActivityComponent
import com.example.gitbrowser.home.models.GitRepo
import com.example.gitbrowser.home.viewModel.HomeViewModel
import com.example.gitbrowser.utils.EndlessRecyclerViewScrollListener


class HomeActivity : AppCompatActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var  repoList: List<GitRepo>
    private  lateinit var repoAdapter: RepoAdapter
    private var query:String = "popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        activityHomeBinding.homeActivity = this

        homeViewModel = ViewModelProvider(this@HomeActivity).get(
            HomeViewModel::class.java
        )

        val homeActivityComponent = DaggerHomeActivityComponent.builder()
            .applicationComponent((application as GitBrowserApplication).applicationComponent)
            .build()
        homeActivityComponent.inject(this)
        homeActivityComponent.inject(homeViewModel)

        setUpPageListener()


        popuateRepo()

        initTextListeners()

    }

    private fun setUpPageListener() {

        val layoutManager = LinearLayoutManager(applicationContext)
        activityHomeBinding.repoRecycler.layoutManager = layoutManager

        activityHomeBinding.repoRecycler.addOnScrollListener(object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                fetchRepositoryForPage(page)
            }

        })
    }

    fun fetchRepositoryForPage( page : Int){
        homeViewModel.fetchRepoForPage(page, query).observe(this@HomeActivity,{it ->
            repoAdapter.addRepos(it.items)
            repoAdapter.notifyDataSetChanged()
        })
    }

    fun initTextListeners()
    {
        activityHomeBinding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)){

                    query = s.toString()
                    homeViewModel.searchTopicFor(s.toString()).observe(this@HomeActivity,{it ->
                        repoList = it.items

                        repoAdapter = RepoAdapter(it.items.toMutableList(),
                            this@HomeActivity)

                        activityHomeBinding.repoRecycler.adapter = repoAdapter
                        repoAdapter.notifyDataSetChanged()
                    })
                }
                else
                    popuateRepo()
            }

        })
    }



    fun popuateRepo()
     {
         query = "popular"
         homeViewModel.trendingRepo.observe(this,{it ->
             repoList = it.items
             repoAdapter = RepoAdapter(it.items.toMutableList(),
             this)

             activityHomeBinding.repoRecycler.itemAnimator = DefaultItemAnimator()

             activityHomeBinding.repoRecycler.adapter = repoAdapter
             repoAdapter.notifyDataSetChanged()
         })
     }
}