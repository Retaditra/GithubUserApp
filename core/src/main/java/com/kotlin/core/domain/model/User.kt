package com.kotlin.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val isFavorite: Boolean
) : Parcelable
