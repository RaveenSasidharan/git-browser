package com.example.gitbrowser.di.module

import android.content.Context
import androidx.room.Room
import com.example.gitbrowser.BuildConfig
import com.example.gitbrowser.db.GitBrowserDatabase
import com.example.gitbrowser.di.scope.DatabaseInfo
import com.example.gitbrowser.utils.Constants
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
@Module
class ApplicationModule(var mContext: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return mContext
    }

    @Provides
    @Singleton
    @Named(Constants.NEWS_FEED_API_HEADERS)
    fun provideMovieOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }


    @Provides
    @Singleton
    @Named(Constants.RETROFIT_WITH_HEADERS)
    fun provideRetrofitWithHeaders(
        @Named(Constants.GSON_OF_GOOGLE) gson: Gson,
        @Named(Constants.NEWS_FEED_API_HEADERS) okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    @Singleton
    @Named(Constants.RETROFIT_WITHOUT_HEADERS)
    fun provideRetrofitWithoutHeaders(@Named(Constants.GSON_OF_GOOGLE) gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }



    @Provides
    @Singleton
    @Named(Constants.GSON_OF_RETROFIT)
    fun provideRetrofitGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    @Named(Constants.GSON_OF_GOOGLE)
    fun provideGoogleGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return Constants.Db.DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): GitBrowserDatabase {
        return Room.databaseBuilder(context, GitBrowserDatabase::class.java, dbName)
            .fallbackToDestructiveMigration().build()
    }

}