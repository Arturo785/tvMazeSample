package com.arturo.tvmazesample.schedule_list.presentation.states

import com.arturo.tvmazesample.schedule_list.domain.models.ScheduleResponseItem

data class ScheduleListState(
    val scheduleList: List<ScheduleResponseItem> = emptyList(),
    val selectedItem: ScheduleResponseItem? = null,
    val isDatePickerVisible: Boolean = false,
    val currentDate: String? = null,
    val isLoading: Boolean = false
)