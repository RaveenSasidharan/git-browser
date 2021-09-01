package com.example.gitbrowser.home.interfaces

import com.example.gitbrowser.home.models.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {

    @GET("/search/repositories")
    fun getRepoList(@Query("q") q: String):Call<RepoSearchResponse>


}