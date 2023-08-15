package com.kotlin.githubuserapp.main

import androidx.lifecycle.*
import com.kotlin.core.data.Resource
import com.kotlin.core.domain.model.User
import com.kotlin.core.domain.usecase.UserUseCase

class MainViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _query = MutableLiveData<String>()

    private val _user = _query.switchMap { query ->
        liveData {
            emit(Resource.Loading())
            emitSource(userUseCase.getUser(query).asLiveData())
        }
    }

    val user: LiveData<Resource<List<User>>> = _user

    var thisQuery = "arif"
        set(value) {
            _query.value = value
            field = value
        }

    init {
        _query.value = thisQuery
    }
}


