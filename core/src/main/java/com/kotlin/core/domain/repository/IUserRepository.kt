package com.kotlin.core.domain.repository

import com.kotlin.core.data.Resource
import com.kotlin.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getUser(query: String): Flow<Resource<List<User>>>

    fun getDetail(username: String): Flow<Resource<User>>

    fun getFollowers(username: String): Flow<Resource<List<User>>>

    fun getFollowing(username: String): Flow<Resource<List<User>>>

    fun getFavorite(): Flow<List<User>>

    fun setFavorite(user: User, state: Boolean)
}