package com.kotlin.githubuserapp.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kotlin.core.domain.usecase.UserUseCase

class FollowingViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getFollowing(username: String) = userUseCase.getFollowing(username).asLiveData()
}