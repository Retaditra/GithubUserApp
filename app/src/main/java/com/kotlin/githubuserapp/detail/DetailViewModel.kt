package com.kotlin.githubuserapp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kotlin.core.domain.model.User
import com.kotlin.core.domain.usecase.UserUseCase

class DetailViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    fun userDetail(username: String) = userUseCase.getDetail(username).asLiveData()

    fun setFavorite(user: User, newStatus: Boolean) =
        userUseCase.setFavorite(user, newStatus)
}