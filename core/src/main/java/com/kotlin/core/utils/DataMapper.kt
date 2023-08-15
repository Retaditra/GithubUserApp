package com.kotlin.core.utils

import com.kotlin.core.data.source.local.entity.UserEntity
import com.kotlin.core.data.source.remote.respone.DetailResponse
import com.kotlin.core.data.source.remote.respone.UserListResponse
import com.kotlin.core.domain.model.User

object DataMapper {
    fun responseToUser(it: UserListResponse): User = User(
        id = it.id,
        username = it.username,
        avatarUrl = it.avatarUrl,
        followers = 0,
        following = 0,
        isFavorite = false,
    )

    fun detailToUser(it: DetailResponse): User = User(
        id = it.id,
        username = it.username,
        avatarUrl = it.avatarUrl,
        followers = it.followers,
        following = it.following,
        isFavorite = false,
    )

    fun entityToUser(it: UserEntity): User = User(
        id = it.id,
        username = it.username,
        avatarUrl = it.avatarUrl,
        followers = it.followers,
        following = it.following,
        isFavorite = it.isFavorite
    )

    fun entityToUserList(it: List<UserEntity>): List<User> = it.map {
        User(
            id = it.id,
            username = it.username,
            avatarUrl = it.avatarUrl,
            followers = it.followers,
            following = it.following,
            isFavorite = it.isFavorite,
        )
    }

    fun userToEntity(it: User) = UserEntity(
        id = it.id,
        username = it.username,
        avatarUrl = it.avatarUrl,
        followers = it.followers,
        following = it.following,
        isFavorite = it.isFavorite
    )
}