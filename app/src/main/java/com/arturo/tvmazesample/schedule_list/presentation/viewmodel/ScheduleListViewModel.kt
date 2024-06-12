package com.arturo.tvmazesample.schedule_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturo.tvmazesample.core.domain.ResultWrapper
import com.arturo.tvmazesample.schedule_list.domain.repository.ScheduleListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val scheduleListRepository: ScheduleListRepository,
    // private val scope: CoroutineScope = viewModelScope check this
) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            val response = scheduleListRepository.getScheduleList(
                country = "US",
                date = "2014-12-01"
            )
            when (response) {
                is ResultWrapper.GenericError -> {
                    val test = 1
                    // manage error
                }

                is ResultWrapper.NetworkError -> {
                    val test = 1
                    // manage error
                }

                is ResultWrapper.Success -> {
                    val a = response.value
                    val test = 1
                }
            }
        }
    }

}