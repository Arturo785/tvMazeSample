package com.arturo.tvmazesample.schedule_list.domain.models


import com.google.gson.annotations.SerializedName

/**
 *  Not the best practice to have non kotlin code on domain but seems it's only for serialization
 *  can be allowed
 * */

data class ScheduleResponseItem(
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: Image?,
    @SerializedName("_links")
    val links: Links,
    val name: String,
    val number: Int?,
    val rating: Rating,
    val runtime: Int,
    val season: Int,
    val show: ShowHolder,
    val summary: String?,
    val type: String,
    val url: String
)