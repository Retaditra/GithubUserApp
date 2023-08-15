package com.kotlin.githubuserapp.di

import com.kotlin.core.domain.usecase.UserInteract
import com.kotlin.core.domain.usecase.UserUseCase
import com.kotlin.githubuserapp.detail.DetailViewModel
import com.kotlin.githubuserapp.followers.FollowersViewModel
import com.kotlin.githubuserapp.following.FollowingViewModel
import com.kotlin.githubuserapp.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteract(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FollowersViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}