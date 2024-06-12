package com.arturo.tvmazesample.schedule_list.di

import com.arturo.tvmazesample.schedule_list.data.remote.TvMazeApi
import com.arturo.tvmazesample.schedule_list.data.repository.ScheduleListRepositoryImpl
import com.arturo.tvmazesample.schedule_list.domain.repository.ScheduleListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ScheduleViewModelModule {

    @Provides
    fun provideScheduleListRepository(tvMazeApi: TvMazeApi): ScheduleListRepository {
        return ScheduleListRepositoryImpl(
            tvMazeApi = tvMazeApi
        )
    }
}