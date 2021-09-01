package com.example.gitbrowser.home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitbrowser.home.models.RepoSearchResponse
import com.example.gitbrowser.home.repo.HomeRepository
import dagger.Module
import javax.inject.Inject

@Module
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val errorData = MutableLiveData<Throwable>()

    @Inject
    lateinit var homeRepository: HomeRepository


    val trendingRepo : LiveData<RepoSearchResponse>
        get() {
            val movieListResponse = MutableLiveData<RepoSearchResponse>()
            homeRepository.getRepo(movieListResponse, errorData)
            return movieListResponse
        }



    fun  searchTopicFor(query : String): LiveData<RepoSearchResponse>
    {
        val movieListResponse = MutableLiveData<RepoSearchResponse>()
        homeRepository.getRepoWithQuery(movieListResponse, errorData, query)
        return movieListResponse
    }




}