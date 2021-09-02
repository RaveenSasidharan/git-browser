package com.example.gitbrowser.home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitbrowser.home.models.RepoSearchResponse
import com.example.gitbrowser.home.repo.HomeRepository
import dagger.Module
import javax.inject.Inject


class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val errorData = MutableLiveData<Throwable>()

    @Inject
    lateinit var homeRepository: HomeRepository


    val trendingRepo : LiveData<RepoSearchResponse>
        get() {
            val repoListLiveData = MutableLiveData<RepoSearchResponse>()
            homeRepository.getRepo(repoListLiveData, errorData)
            return repoListLiveData
        }



    fun  searchTopicFor(query : String): LiveData<RepoSearchResponse>
    {
        val repoListLiveData = MutableLiveData<RepoSearchResponse>()
        homeRepository.getRepoWithQuery(repoListLiveData, errorData, query)
        return repoListLiveData
    }


    fun  fetchRepoForPage(page : Int, query: String): LiveData<RepoSearchResponse>
    {
        val repoListLiveData = MutableLiveData<RepoSearchResponse>()
        homeRepository.getRepoWithQueryPage(repoListLiveData, errorData, query, page)
        return repoListLiveData
    }


}