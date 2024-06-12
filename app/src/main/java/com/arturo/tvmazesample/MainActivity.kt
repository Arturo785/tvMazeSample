package com.arturo.tvmazesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arturo.tvmazesample.schedule_list.presentation.ScheduleListDetail
import com.arturo.tvmazesample.schedule_list.presentation.ScheduleListScreen
import com.arturo.tvmazesample.schedule_list.presentation.ui.ScheduleListDetailItem
import com.arturo.tvmazesample.schedule_list.presentation.ui.ScheduleListScreenComposable
import com.arturo.tvmazesample.schedule_list.presentation.viewmodel.ScheduleListViewModel
import com.arturo.tvmazesample.theme.TvMazeSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<ScheduleListViewModel>()
        setContent {
            TvMazeSampleTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ScheduleListScreen,
                    ) {
                        composable<ScheduleListScreen> {
                            ScheduleListScreenComposable(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = viewModel,
                                snackbarHostState = snackbarHostState,
                                onNavigation = {
                                    navController.navigate(ScheduleListDetail)
                                })
                        }
                        composable<ScheduleListDetail> {
                            viewModel.scheduleListState.value.selectedItem?.let { item ->
                                ScheduleListDetailItem(item = item)
                            }
                        }
                    }
                }
            }
        }
    }
}
