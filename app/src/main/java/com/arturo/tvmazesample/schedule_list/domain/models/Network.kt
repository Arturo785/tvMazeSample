package com.arturo.tvmazesample.schedule_list.domain.models


data class Network(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String?
)