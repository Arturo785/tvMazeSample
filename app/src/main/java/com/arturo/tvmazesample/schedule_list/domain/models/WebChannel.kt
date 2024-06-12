package com.arturo.tvmazesample.schedule_list.domain.models


data class WebChannel(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String?
)