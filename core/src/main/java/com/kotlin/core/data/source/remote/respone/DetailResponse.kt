package com.kotlin.core.data.source.remote.respone

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,
)