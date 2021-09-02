package com.example.gitbrowser.home.interfaces

import com.example.gitbrowser.home.models.Contributors
import com.example.gitbrowser.home.models.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

import okhttp3.ResponseBody




interface HomeApiInterface {

    @GET("/search/repositories")
    fun getRepoList(@Query("q") q: String, @Query("per_page") per_page: Int):Call<RepoSearchResponse>

    @GET("/search/repositories")
    fun getRepoListPage(@Query("q") q: String, @Query("per_page") per_page: Int, @Query("page") page:Int):Call<RepoSearchResponse>

    @GET
    fun getContributorsList(@Url url: String): Call<List<Contributors>>
}