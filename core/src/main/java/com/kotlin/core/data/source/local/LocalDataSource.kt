package com.kotlin.core.data.source.local

import com.kotlin.core.data.source.local.entity.UserEntity
import com.kotlin.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {

    fun getUsername(username: String): Flow<UserEntity>? = userDao.getUsername(username)

    fun getFavorite(): Flow<List<UserEntity>> = userDao.getFavorite()

    fun setFavorite(user: UserEntity, newState: Boolean) {
        if (newState) {
            user.isFavorite = newState
            userDao.apply {
                insertUser(user)
                updateUser(user)
            }
        } else {
            userDao.deleteUser(user)
        }
    }
}