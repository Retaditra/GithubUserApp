package com.kotlin.core.data.source.remote.network

import com.kotlin.core.data.source.remote.respone.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): UserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): DetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<UserListResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<UserListResponse>
}