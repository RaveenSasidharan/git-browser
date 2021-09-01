package com.example.gitbrowser.home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitbrowser.home.models.Contributors
import com.example.gitbrowser.home.models.RepoSearchResponse
import com.example.gitbrowser.home.repo.HomeRepository
import javax.inject.Inject

class RepoDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val errorData = MutableLiveData<Throwable>()

    @Inject
    lateinit var homeRepository: HomeRepository

    fun getContributors(url : String): LiveData<List<Contributors>>
    {
        val contributorsLiveData = MutableLiveData<List<Contributors>>()
        homeRepository.getContributors(contributorsLiveData, errorData, url)
        return contributorsLiveData
    }
}
