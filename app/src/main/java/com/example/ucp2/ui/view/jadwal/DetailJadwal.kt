package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.HomeScreenViewModel
import com.example.ucp2.ui.viewmodel.JadwalUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.UpdateJadwalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun UpdateJadwalView(
    onBack: () -> Unit,
    onNavigate:() -> Unit,
    modifier: Modifier = Modifier,
    updateJadwalViewModel: UpdateJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory ),
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = updateJadwalViewModel.updateUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val homeDokterUIState by homeScreenViewModel.homeUIState.collectAsState()
    val listDokter = homeDokterUIState.listDokter

    var selectedDokter by remember { mutableStateOf("") }

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                updateJadwalViewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Update Jadwal",
            )
        }
    ) {
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            InsertBodyjadwal(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    updateJadwalViewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (updateJadwalViewModel.validateFields()){
                            updateJadwalViewModel.updateData(namaDokter = selectedDokter)
                            onNavigate()
                        }
                    }
                },
                listDokter = listDokter
            )
        }
    }


}