package com.arturo.tvmazesample.schedule_list.di

import com.arturo.tvmazesample.BuildConfig
import com.arturo.tvmazesample.schedule_list.data.remote.TvMazeApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ScheduleModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder() = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideTvMazeApi(
        gson: Gson,
        client: OkHttpClient
    ): TvMazeApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(TvMazeApi::class.java)
    }
}