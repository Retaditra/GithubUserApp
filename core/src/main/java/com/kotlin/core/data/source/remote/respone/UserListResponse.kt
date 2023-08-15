package com.kotlin.core.data.source.remote.respone

import com.google.gson.annotations.SerializedName

data class UserListResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)