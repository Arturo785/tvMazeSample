package com.arturo.tvmazesample.schedule_list.data.repository

import com.arturo.tvmazesample.core.data.safeApiCall
import com.arturo.tvmazesample.core.domain.ResultWrapper
import com.arturo.tvmazesample.schedule_list.data.remote.TvMazeApi
import com.arturo.tvmazesample.schedule_list.domain.models.ScheduleResponseItem
import com.arturo.tvmazesample.schedule_list.domain.repository.ScheduleListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ScheduleListRepositoryImpl @Inject constructor(
    private val tvMazeApi: TvMazeApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScheduleListRepository {
    override suspend fun getScheduleList(
        country: String,
        date: String
    ): ResultWrapper<List<ScheduleResponseItem>> {
        return safeApiCall(dispatcher = dispatcher) {
            tvMazeApi.getSchedule(
                country = country,
                date = date
            )
        }
    }
}