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
import com.example.gitbrowser.GitBrowserApplication
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.ActivityHomeBinding
import com.example.gitbrowser.home.home.di.DaggerHomeActivityComponent
import com.example.gitbrowser.home.models.GitRepo
import com.example.gitbrowser.home.viewModel.HomeViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var  repoList: List<GitRepo>
    private  lateinit var repoAdapter: RepoAdapter
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

        popuateRepo()

        initTextListeners()
    }

    fun initTextListeners()
    {
        activityHomeBinding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s))
                    homeViewModel.searchTopicFor(s.toString()).observe(this@HomeActivity,{it ->
                        repoList = it.items

                        repoAdapter = RepoAdapter(it.items,
                            this@HomeActivity)

                        activityHomeBinding.repoRecycler.adapter = repoAdapter
                        repoAdapter.notifyDataSetChanged()
                    })
                else
                    popuateRepo()
            }

        })
    }



    fun popuateRepo()
     {
         homeViewModel.trendingRepo.observe(this,{it ->
             repoList = it.items
             repoAdapter = RepoAdapter(it.items,
             this)
             val layoutManager = LinearLayoutManager(applicationContext)
             activityHomeBinding.repoRecycler.layoutManager = layoutManager
             activityHomeBinding.repoRecycler.itemAnimator = DefaultItemAnimator()

             activityHomeBinding.repoRecycler.adapter = repoAdapter
             repoAdapter.notifyDataSetChanged()
         })
     }
}