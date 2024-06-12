package com.arturo.tvmazesample.schedule_list.domain.models


data class LinksOuter(
    val nextepisode: Nextepisode?,
    val previousepisode: Previousepisode,
    val self: Self
)