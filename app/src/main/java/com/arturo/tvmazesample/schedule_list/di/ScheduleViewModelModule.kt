package com.arturo.tvmazesample.schedule_list.di

import com.arturo.tvmazesample.schedule_list.data.remote.TvMazeApi
import com.arturo.tvmazesample.schedule_list.data.repository.DispatcherProviderImpl
import com.arturo.tvmazesample.schedule_list.data.repository.ScheduleListRepositoryImpl
import com.arturo.tvmazesample.schedule_list.domain.repository.DispatcherProvider
import com.arturo.tvmazesample.schedule_list.domain.repository.ScheduleListRepository
import com.arturo.tvmazesample.schedule_list.domain.use_case.TimeFormatterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ScheduleViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideScheduleListRepository(tvMazeApi: TvMazeApi): ScheduleListRepository {
        return ScheduleListRepositoryImpl(
            tvMazeApi = tvMazeApi
        )
    }

    @Provides
    fun provideDispatchers(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideTimeFormatterUseCase(): TimeFormatterUseCase {
        return TimeFormatterUseCase()
    }
}