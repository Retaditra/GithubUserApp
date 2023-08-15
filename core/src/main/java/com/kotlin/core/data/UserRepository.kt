package com.kotlin.core.data

import com.kotlin.core.data.source.local.LocalDataSource
import com.kotlin.core.data.source.remote.RemoteDataSource
import com.kotlin.core.data.source.remote.network.ApiResponse
import com.kotlin.core.domain.model.User
import com.kotlin.core.domain.repository.IUserRepository
import com.kotlin.core.utils.AppExecutors
import com.kotlin.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

    override fun getUser(query: String): Flow<Resource<List<User>>> = flow {
        when (val response = remoteDataSource.getUser(query).first()) {
            is ApiResponse.Success -> {
                val users = response.data.map {
                    DataMapper.responseToUser(it)
                }
                emit(Resource.Success(users))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("User not found"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }


    override fun getDetail(username: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val localUser = localDataSource.getUsername(username)?.first()
        if (localUser != null) {
            emit(Resource.Success(DataMapper.entityToUser(localUser)))
        } else {
            when (val response = remoteDataSource.getDetail(username).first()) {
                is ApiResponse.Success -> {
                    val user = DataMapper.detailToUser(response.data)
                    emit(Resource.Success(user))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
                is ApiResponse.Empty -> {}
            }
        }
    }

    override fun getFollowers(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getFollowers(username).first()) {
            is ApiResponse.Success -> {
                val followers = response.data.map {
                    DataMapper.responseToUser(it)
                }
                emit(Resource.Success(followers))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun getFollowing(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getFollowing(username).first()) {
            is ApiResponse.Success -> {
                val following = response.data.map {
                    DataMapper.responseToUser(it)
                }
                emit(Resource.Success(following))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun getFavorite(): Flow<List<User>> {
        return localDataSource.getFavorite().map {
            DataMapper.entityToUserList(it)
        }
    }

    override fun setFavorite(user: User, state: Boolean) {
        val userEntity = DataMapper.userToEntity(user)
        appExecutors.diskIO().execute {
            localDataSource.setFavorite(userEntity, state)
        }
    }
}