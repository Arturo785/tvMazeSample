package com.arturo.tvmazesample.schedule_list.domain.repository

import com.arturo.tvmazesample.core.domain.ResultWrapper
import com.arturo.tvmazesample.schedule_list.domain.models.ScheduleResponseItem

interface ScheduleListRepository {
    suspend fun getScheduleList(
        country: String,
        date: String
    ): ResultWrapper<List<ScheduleResponseItem>>
}