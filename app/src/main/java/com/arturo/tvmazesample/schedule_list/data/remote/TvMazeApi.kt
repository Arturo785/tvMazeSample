package com.arturo.tvmazesample.schedule_list.data.remote

import com.arturo.tvmazesample.schedule_list.domain.models.ScheduleResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeApi {
    @GET("schedule")
    suspend fun getSchedule(
        @Query("country") country: String,
        @Query("date") date: String,
    ): List<ScheduleResponseItem>
}