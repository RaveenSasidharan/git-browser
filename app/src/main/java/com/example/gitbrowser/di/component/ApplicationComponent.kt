package com.example.gitbrowser.di.component

import android.content.Context
import com.example.gitbrowser.db.GitBrowserDatabase
import com.example.gitbrowser.di.module.ApplicationModule
import com.example.gitbrowser.utils.Constants
import com.google.gson.Gson
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @Named(Constants.RETROFIT_WITHOUT_HEADERS)
    fun provideRetrofitWithoutHeaders(): Retrofit

    @Named(Constants.RETROFIT_WITH_HEADERS)
    fun provideWithHeaders(): Retrofit

    @Named(Constants.GSON_OF_GOOGLE)
    fun provideGsonOfGoogle(): Gson

    fun providesContext(): Context

    fun provideAppDatabase(): GitBrowserDatabase
}