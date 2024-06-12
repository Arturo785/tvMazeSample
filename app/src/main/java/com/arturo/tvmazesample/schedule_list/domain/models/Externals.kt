package com.arturo.tvmazesample.schedule_list.domain.models


import com.google.gson.annotations.SerializedName

data class Externals(
    val imdb: String?,
    val thetvdb: Int?,
    val tvrage: Int?
)