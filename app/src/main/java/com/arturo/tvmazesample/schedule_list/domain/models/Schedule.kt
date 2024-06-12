package com.arturo.tvmazesample.schedule_list.domain.models


import com.google.gson.annotations.SerializedName

data class Schedule(
    val days: List<String>,
    val time: String
)