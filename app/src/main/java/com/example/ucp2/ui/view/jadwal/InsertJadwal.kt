package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.ui.utils.DynamicSelectedTextField
import com.example.ucp2.ui.viewmodel.FormErrorStateJadwal
import com.example.ucp2.ui.viewmodel.HomeScreenViewModel
import com.example.ucp2.ui.viewmodel.InsertJadwalViewModel
import com.example.ucp2.ui.viewmodel.JadwalEvent
import com.example.ucp2.ui.viewmodel.JadwalUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertJadwal(
    insertJadwalViewModel: InsertJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    onJadwal:() -> Unit,
    onDokter:() -> Unit,
    modifier: Modifier = Modifier,


    ) {

    val jadwalUiState = insertJadwalViewModel.jadwalUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val homeUiState by homeScreenViewModel.homeUIState.collectAsState()
    val listDokter = homeUiState.listDokter

    LaunchedEffect(jadwalUiState.snackBarMessage) {
        jadwalUiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                insertJadwalViewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            com.example.ucp2.ui.customwidget.TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah jadwal",
            )
            InsertBodyjadwal(
                uiState = jadwalUiState,
                onValueChange = { updatedEvent ->
                    insertJadwalViewModel.updateState(updatedEvent)
                },
                onClick = {
                    insertJadwalViewModel.saveData()
                    onNavigate()
                },
                listDokter = listDokter
            )
        }
    }

}

@Composable
fun InsertBodyjadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit = {},
    uiState: JadwalUiState = JadwalUiState(),
    onClick: () -> Unit,
    listDokter: List<Dokter> = emptyList(),

    ) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            listDokter = listDokter,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            Text(text = "Simpan", fontSize = 16.sp)
        }
    }
}


@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit = {},
    errorState: FormErrorStateJadwal = FormErrorStateJadwal(),
    listDokter: List<Dokter> = emptyList(),
    modifier: Modifier = Modifier
){
    Box() {
        Column(modifier = modifier.fillMaxWidth().padding(top = 20.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.idJadwal,
                onValueChange = { onValueChange(jadwalEvent.copy(idJadwal = it)) },
                label = { Text("idJadwal") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.idJadwal != null,
                placeholder = { Text("Masukkan id Jadwal") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.idJadwal ?: "", color = Color.Red)

            DynamicSelectedTextField(
                selectedValue = jadwalEvent.namaDokter,
                listDokter = listDokter,
                label = "Pilih Dokter",
                onValueChangedEvent = { selectedDokter ->
                    onValueChange(jadwalEvent.copy(namaDokter = selectedDokter))
                },
                onCLick = {},
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier
                .padding(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.namaPasien,
                onValueChange = { onValueChange(jadwalEvent.copy(namaPasien = it)) },
                label = { Text("Nama Pasien") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.namaPasien != null,
                placeholder = { Text("Masukkan nama Pasien") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.namaPasien ?: "", color = Color.Red)


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.noHp,
                onValueChange = { onValueChange(jadwalEvent.copy(noHp = it)) },
                label = { Text("NO HP") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.noHp != null,
                placeholder = { Text("Masukkan No HP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.noHp ?: "", color = Color.Red)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.tanggalKonsul,
                onValueChange = { onValueChange(jadwalEvent.copy(tanggalKonsul = it)) },
                label = { Text("Tanggal Konsul") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.tanggalKonsul != null,
                placeholder = { Text("Tentukan Tanggal") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.tanggalKonsul ?: "", color = Color.Red)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jadwalEvent.status,
                onValueChange = { onValueChange(jadwalEvent.copy(status = it)) },
                label = { Text("Status") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.status != null,
                placeholder = { Text("Masukkan status pasien") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.status ?: "", color = Color.Red)


        }
    }
}