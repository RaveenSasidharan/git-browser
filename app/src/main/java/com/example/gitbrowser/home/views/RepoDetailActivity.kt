package com.example.gitbrowser.home.views

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gitbrowser.BuildConfig
import com.example.gitbrowser.GitBrowserApplication
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.ActivityRepoDetailsBinding
import com.example.gitbrowser.home.home.di.DaggerHomeActivityComponent
import com.example.gitbrowser.home.models.GitRepo
import com.example.gitbrowser.home.viewModel.RepoDetailsViewModel

class RepoDetailActivity : AppCompatActivity() {

    private lateinit var activityRepoDetailsBinding: ActivityRepoDetailsBinding
    private lateinit var repoDetailsViewModel: RepoDetailsViewModel

    private lateinit var gitRepo: GitRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRepoDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_repo_details)
        activityRepoDetailsBinding.repoDetailsActivity = this

        repoDetailsViewModel = ViewModelProvider(this@RepoDetailActivity).get(
            RepoDetailsViewModel::class.java
        )

        val homeActivityComponent = DaggerHomeActivityComponent.builder()
            .applicationComponent((application as GitBrowserApplication).applicationComponent)
            .build()
        homeActivityComponent.inject(this)
        homeActivityComponent.inject(repoDetailsViewModel)

        gitRepo = intent.extras?.getSerializable("repo") as GitRepo

        initViews()
    }

    private fun initViews() {
        activityRepoDetailsBinding.repoTitle.text = gitRepo.full_name
        activityRepoDetailsBinding.link.text = gitRepo.html_url
        activityRepoDetailsBinding.aboutText.text = gitRepo.description
       // activityRepoDetailsBinding.license.text = gitRepo.license.name

        Glide
            .with(this)
            .load(gitRepo.owner?.avatar_url)
            .into(activityRepoDetailsBinding.image)

        activityRepoDetailsBinding.link.setPaintFlags(activityRepoDetailsBinding.link.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)


        var url = gitRepo.contributors_url?.replace(BuildConfig.BASE_URL, "")
        url?.let {
            repoDetailsViewModel.getContributors(it).observe(this, {
                var contributorAdapter = ContributorAdapter(it)

                activityRepoDetailsBinding.contributorRecycler.adapter = contributorAdapter
                contributorAdapter.notifyDataSetChanged()
            })
        }

    }

    fun back()
    {
        finish()
    }


    fun openLicense()
    {
     /*   if (gitRepo.license == null) {
            return
        }
        val intent : Intent = Intent(this,BrowserActivity::class.java)
        val bundle : Bundle = Bundle()
        bundle.putString("title", gitRepo.license.name)
        bundle.putString("url", gitRepo.license.url)
        intent.putExtras(bundle)
        startActivity(intent)*/
    }

    fun openGitRepo()
    {
        val intent : Intent = Intent(this,BrowserActivity::class.java)
        val bundle : Bundle = Bundle()
        bundle.putString("title", gitRepo.full_name)
        bundle.putString("url", gitRepo.html_url)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}