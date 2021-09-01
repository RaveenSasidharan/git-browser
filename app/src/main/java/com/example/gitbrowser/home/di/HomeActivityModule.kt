package com.example.gitbrowser.home.di

import android.content.Context
import com.example.gitbrowser.db.GitBrowserDatabase
import com.example.gitbrowser.di.scope.UserScope
import com.example.gitbrowser.home.interfaces.HomeApiInterface
import com.example.gitbrowser.home.repo.HomeRepository
import com.example.gitbrowser.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class HomeActivityModule {

    @Provides
    @UserScope
    fun providesHomeApiInterface(@Named(Constants.RETROFIT_WITH_HEADERS) retrofit: Retrofit): HomeApiInterface {
        return retrofit.create(HomeApiInterface::class.java)
    }

    @Provides
    @UserScope
    fun provideHomeRepository(
        homeApiInterface: HomeApiInterface,
        gitBrowserDatabase: GitBrowserDatabase,
        context: Context
    ): HomeRepository {
        val homeActivityRepository = HomeRepository.getInstance()
        homeActivityRepository.setUp(homeApiInterface, context, gitBrowserDatabase)
        return homeActivityRepository
    }
}