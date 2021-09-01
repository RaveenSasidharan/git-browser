package com.example.gitbrowser.home.home.di

import com.example.gitbrowser.di.component.ApplicationComponent
import com.example.gitbrowser.di.scope.UserScope
import com.example.gitbrowser.home.di.HomeActivityModule
import com.example.gitbrowser.home.viewModel.HomeViewModel
import com.example.gitbrowser.home.viewModel.RepoDetailsViewModel
import com.example.gitbrowser.home.views.HomeActivity
import com.example.gitbrowser.home.views.RepoDetailActivity
import dagger.Component

@UserScope
@Component(dependencies = [ApplicationComponent::class], modules = [HomeActivityModule::class])
interface HomeActivityComponent {

    fun inject(homeActivity: HomeActivity)

    fun inject(repoDetailActivity: RepoDetailActivity)

    fun inject(repoDetailsViewModel: RepoDetailsViewModel)

    fun inject(homeViewModel: HomeViewModel)
}