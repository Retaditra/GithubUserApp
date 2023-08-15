package com.kotlin.core.di

import androidx.room.Room
import com.kotlin.core.BuildConfig
import com.kotlin.core.data.UserRepository
import com.kotlin.core.data.source.local.LocalDataSource
import com.kotlin.core.data.source.local.room.UserDatabase
import com.kotlin.core.data.source.remote.RemoteDataSource
import com.kotlin.core.data.source.remote.network.ApiService
import com.kotlin.core.domain.repository.IUserRepository
import com.kotlin.core.utils.AppExecutors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, "GithubUser.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", BuildConfig.KEY)
                .build()
            chain.proceed(requestHeaders)
        }
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IUserRepository> {
        UserRepository(get(), get(), get())
    }
}
