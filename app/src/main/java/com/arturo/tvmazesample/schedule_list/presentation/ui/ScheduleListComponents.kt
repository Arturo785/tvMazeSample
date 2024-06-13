package com.arturo.tvmazesample.schedule_list.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturo.tvmazesample.R
import com.arturo.tvmazesample.core.domain.LocalSpacing
import com.arturo.tvmazesample.core.domain.SEARCH_REGION_API
import com.arturo.tvmazesample.schedule_list.domain.models.ScheduleResponseItem
import com.arturo.tvmazesample.schedule_list.presentation.viewmodel.ScheduleListViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ScheduleListScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: ScheduleListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigation: () -> Unit = {}
) {
    val items by viewModel.scheduleListState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.eventFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is ScheduleListViewModel.UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(
                    uiEvent.message
                )
            }
        }
    }

    if (items.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicatorCustom()
        }
    }

    Column(modifier = modifier) {
        val dimens = LocalSpacing.current

        AnimatedVisibility(
            visible = items.isDatePickerVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            DatePickerSample(onDateSelected = { timeInMilis ->
                val dateFormatted = viewModel.getFormattedDate(timeInMilis)
                viewModel.retrieveScheduleList(
                    country = SEARCH_REGION_API,
                    date = dateFormatted
                )
                viewModel.changePickerVisibility(!items.isDatePickerVisible)
            })
        }
        Text(
            text = items.currentDate.orEmpty(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = { viewModel.changePickerVisibility(!items.isDatePickerVisible) }) {
            val text =
                if (items.isDatePickerVisible) stringResource(R.string.dismiss_description) else stringResource(
                    R.string.pick_date_description
                )
            Text(text = text)
        }
        LazyColumn() {
            items(items.scheduleList) { responseItem ->
                ScheduleListItem(
                    scheduleItem = responseItem,
                    modifier = Modifier
                        .padding(dimens.spaceSmall)
                        .clip(RoundedCornerShape(10))
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    onItemClick = {
                        viewModel.setSelectedScheduleItem(responseItem)
                        onNavigation.invoke()
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ScheduleListItem(
    scheduleItem: ScheduleResponseItem,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {}
) {
    val dimens = LocalSpacing.current
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onItemClick.invoke()
        }) {
        GlideImage(
            model = scheduleItem.image?.medium ?: R.drawable.ic_launcher_foreground,
            contentDescription = scheduleItem.name,
            alignment = Alignment.CenterStart,
            modifier = modifier.fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(dimens.spaceExtraSmall))
        Column {
            Text(
                text = scheduleItem.name,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(dimens.spaceSmall))
            Text(
                text = stringResource(R.string.airdate_text).plus(scheduleItem.airdate),
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(dimens.spaceExtraSmall))
            Text(
                text = stringResource(R.string.airtime_text).plus(scheduleItem.airtime),
                maxLines = 2
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ScheduleListDetailItem(
    item: ScheduleResponseItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val uriHandler = LocalUriHandler.current
        GlideImage(
            model = item.image?.original ?: R.drawable.ic_launcher_foreground,
            contentDescription = item.name,
            alignment = Alignment.CenterStart,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
        )
        Text(
            text = item.name,
            maxLines = 2,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.airdate_text).plus(item.airdate),
        )
        Text(
            text = stringResource(R.string.airtime_text).plus(item.airtime),
        )
        Text(
            text = stringResource(R.string.language_text).plus(item.show.language),
        )
        val days = item.show.schedule.days.joinToString(", ")
        Column {
            Text(text = stringResource(id = R.string.schedule_text))
            Text(text = days)
        }


        if (item.show.officialSite != null) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                uriHandler.openUri(item.show.officialSite)
            }) {
                Text(text = stringResource(R.string.go_to_website_description))
            }
        }
    }
}

@Composable
fun CircularProgressIndicatorCustom(modifier: Modifier = Modifier) {
    val strokeWidth = 5.dp

    CircularProgressIndicator(
        modifier = modifier.drawBehind {
            drawCircle(
                Color.Red,
                radius = size.width / 2 - strokeWidth.toPx() / 2,
                style = Stroke(strokeWidth.toPx())
            )
        },
        color = Color.LightGray,
        strokeWidth = strokeWidth
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerSample(onDateSelected: (Long) -> Unit) {
    val dimens = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(dimens.spaceSmall)) {
        val currentTimeInMilis by remember {
            mutableLongStateOf(System.currentTimeMillis())
        }

        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = currentTimeInMilis)
        DatePicker(state = datePickerState, modifier = Modifier.padding(dimens.spaceMedium))

        Button(onClick = {
            onDateSelected(
                datePickerState.selectedDateMillis ?: currentTimeInMilis
            )
        }) {
            Text(text = stringResource(R.string.set_date_description))
        }
    }
}