package com.arturo.tvmazesample.schedule_list.domain.models


import com.google.gson.annotations.SerializedName

data class ShowHolder(
    val averageRuntime: Int,
    val dvdCountry: DvdCountry?,
    val ended: String?,
    val externals: Externals,
    val genres: List<String>,
    val id: Int,
    val image: Image?,
    val language: String,
    @SerializedName("_links")
    val links: LinksOuter,
    val name: String,
    val network: Network?,
    val officialSite: String?,
    val premiered: String,
    val rating: Rating,
    val runtime: Int?,
    val schedule: Schedule,
    val status: String,
    val summary: String?,
    val type: String,
    val updated: Int,
    val url: String,
    val webChannel: WebChannel?,
    val weight: Int
)