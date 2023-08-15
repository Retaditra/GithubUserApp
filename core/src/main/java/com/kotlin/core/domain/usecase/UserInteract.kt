package com.kotlin.core.domain.usecase

import com.kotlin.core.domain.model.User
import com.kotlin.core.domain.repository.IUserRepository

class UserInteract(private val userRepository: IUserRepository) : UserUseCase {

    override fun getUser(query: String) = userRepository.getUser(query)

    override fun getDetail(username: String) = userRepository.getDetail(username)

    override fun getFollowers(username: String) = userRepository.getFollowers(username)

    override fun getFollowing(username: String) = userRepository.getFollowing(username)

    override fun getFavorite() = userRepository.getFavorite()

    override fun setFavorite(user: User, state: Boolean) =
        userRepository.setFavorite(user, state)
}
