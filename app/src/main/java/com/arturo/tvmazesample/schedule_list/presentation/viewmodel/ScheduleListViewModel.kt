package com.arturo.tvmazesample.schedule_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturo.tvmazesample.core.domain.ResultWrapper
import com.arturo.tvmazesample.core.domain.SEARCH_REGION_API
import com.arturo.tvmazesample.schedule_list.domain.models.ScheduleResponseItem
import com.arturo.tvmazesample.schedule_list.domain.repository.DispatcherProvider
import com.arturo.tvmazesample.schedule_list.domain.repository.ScheduleListRepository
import com.arturo.tvmazesample.schedule_list.domain.use_case.TimeFormatterUseCase
import com.arturo.tvmazesample.schedule_list.presentation.states.ScheduleListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val scheduleListRepository: ScheduleListRepository,
    private val timeFormatterUseCase: TimeFormatterUseCase,
    private val dispatchers: DispatcherProvider
) : ViewModel() {
    private val _scheduleListState = MutableStateFlow(ScheduleListState())
    val scheduleListState = _scheduleListState.asStateFlow()

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        retrieveScheduleList(
            country = SEARCH_REGION_API,
            date = timeFormatterUseCase(System.currentTimeMillis())
        )
    }

    fun retrieveScheduleList(country: String, date: String) {
        viewModelScope.launch(dispatchers.main) {
            _scheduleListState.emit(
                _scheduleListState.value.copy(
                    isLoading = true
                )
            )
            val response = scheduleListRepository.getScheduleList(
                country = country,
                date = date
            )
            when (response) {
                is ResultWrapper.GenericError -> {
                    response.errorMessage?.let {
                        _eventFlow.send(UiEvent.ShowSnackbar(response.errorMessage))
                        _scheduleListState.emit(
                            _scheduleListState.value.copy(
                                isLoading = false
                            )
                        )
                    }
                }

                is ResultWrapper.NetworkError -> {
                    response.cause?.let {
                        _eventFlow.send(UiEvent.ShowSnackbar(response.cause))
                        _scheduleListState.emit(
                            _scheduleListState.value.copy(
                                isLoading = false
                            )
                        )
                    }
                }

                is ResultWrapper.Success -> {
                    _scheduleListState.emit(
                        _scheduleListState.value.copy(
                            scheduleList = response.value,
                            currentDate = date,
                            isLoading = false
                        )
                    )
                }
            }
        }
    }

    fun setSelectedScheduleItem(selectedItem: ScheduleResponseItem?) {
        viewModelScope.launch(dispatchers.main) {
            _scheduleListState.emit(
                _scheduleListState.value.copy(
                    selectedItem = selectedItem
                )
            )
        }
    }

    fun changePickerVisibility(value: Boolean) {
        viewModelScope.launch(dispatchers.main) {
            _scheduleListState.emit(
                _scheduleListState.value.copy(
                    isDatePickerVisible = value
                )
            )
        }
    }

    fun getFormattedDate(timeInMilis: Long): String {
        return timeFormatterUseCase.invoke(timeInMilis)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}