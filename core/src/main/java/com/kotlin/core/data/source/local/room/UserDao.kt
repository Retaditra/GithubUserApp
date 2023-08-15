package com.kotlin.core.data.source.local.room

import androidx.room.*
import com.kotlin.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM githubUser WHERE login = :username ")
    fun getUsername(username: String): Flow<UserEntity>?

    @Query("SELECT * FROM githubUser where isFavorite = 1")
    fun getFavorite(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)
}
