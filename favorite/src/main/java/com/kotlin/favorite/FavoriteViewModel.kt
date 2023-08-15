package com.kotlin.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kotlin.core.domain.usecase.UserUseCase

class FavoriteViewModel(userUseCase: UserUseCase) : ViewModel() {
    val getFavorite = userUseCase.getFavorite().asLiveData()
}