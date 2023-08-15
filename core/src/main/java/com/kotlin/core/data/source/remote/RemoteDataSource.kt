package com.kotlin.core.data.source.remote

import android.util.Log
import com.kotlin.core.data.source.remote.network.ApiResponse
import com.kotlin.core.data.source.remote.network.ApiService
import com.kotlin.core.data.source.remote.respone.DetailResponse
import com.kotlin.core.data.source.remote.respone.UserListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getUser(query: String): Flow<ApiResponse<List<UserListResponse>>> =
        flow {
            try {
                val response = apiService.getUsers(query)
                val user = response.items
                if (user.isNotEmpty()) {
                    emit(ApiResponse.Success(user))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetail(username: String?): Flow<ApiResponse<DetailResponse>> =
        flow {
            try {
                val response = apiService.getDetailUser(username.toString())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowers(username: String): Flow<ApiResponse<List<UserListResponse>>> =
        flow {
            try {
                val response = apiService.getFollowers(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowing(username: String): Flow<ApiResponse<List<UserListResponse>>> =
        flow {
            try {
                val response = apiService.getFollowing(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}
