package com.kotlin.githubuserapp.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kotlin.core.domain.usecase.UserUseCase

class FollowersViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getFollowers(username: String) = userUseCase.getFollowers(username).asLiveData()
}