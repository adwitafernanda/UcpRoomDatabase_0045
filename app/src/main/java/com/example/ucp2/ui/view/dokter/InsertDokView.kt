package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.DokterEvent
import com.example.ucp2.ui.viewmodel.DokterUIState
import com.example.ucp2.ui.viewmodel.DokterViewModel
import com.example.ucp2.ui.viewmodel.FormErrorState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertDokView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilkan snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Tempatkan snackbar di scaffold
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dokter",
            )
            // Isi body
            InsertBodyDokter(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // Update state di ViewModel
                },
                onClick = {
                    viewModel.saveData()
                }
            )
        }
    }
}

@Composable
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DokterUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth().padding(1.dp),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding di dalam card
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormDokter(
                    dokterEvent = uiState.dokterEvent,
                    onValueChange = onValueChange,
                    errorState = uiState.isEntryValid,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary // Menggunakan warna tema untuk button
                    )
                ) {
                    Text(text = "Simpan", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val specializations = listOf(
        "Umum" to Color.Black,
        "Anak" to Color.Black,
        "Kandungan" to Color.Black,
        "Gigi" to Color.Black,
        "Bedah" to Color.Black
    )

    var selectedSpecialization by remember { mutableStateOf(dokterEvent.spesialis) }

    Column(
        modifier = modifier.padding(1.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ID Dokter
        TextField(
            value = dokterEvent.idDokter,
            onValueChange = { onValueChange(dokterEvent.copy(idDokter = it)) },
            label = { Text("ID Dokter") },
            isError = errorState.idDokter != null,
        )
        if (errorState.idDokter != null) {
            Text(
                text = errorState.idDokter,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Nama Dokter
        TextField(
            value = dokterEvent.nama,
            onValueChange = { onValueChange(dokterEvent.copy(nama = it)) },
            label = { Text("Nama Dokter") },
            isError = errorState.nama != null
        )
        if (errorState.nama != null) {
            Text(
                text = errorState.nama,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Spesialis
        Text(
            text = "Spesialis",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            specializations.forEach { (spesialis, color) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedSpecialization == spesialis,
                        onClick = {
                            selectedSpecialization = spesialis
                            onValueChange(dokterEvent.copy(spesialis = spesialis))
                        },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = color,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(
                        text = spesialis,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedSpecialization == spesialis) color else Color.Gray
                    )
                }
            }
        }

        // Klinik
        TextField(
            value = dokterEvent.klinik,
            onValueChange = { onValueChange(dokterEvent.copy(klinik = it)) },
            label = { Text("Klinik") },
            isError = errorState.klinik != null
        )
        if (errorState.klinik != null) {
            Text(
                text = errorState.klinik,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // No HP
        TextField(
            value = dokterEvent.noHp,
            onValueChange = { onValueChange(dokterEvent.copy(noHp = it)) },
            label = { Text("No. HP") },
            isError = errorState.noHp != null
        )
        if (errorState.noHp != null) {
            Text(
                text = errorState.noHp,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Jam Kerja
        TextField(
            value = dokterEvent.jamKerja,
            onValueChange = { onValueChange(dokterEvent.copy(jamKerja = it)) },
            label = { Text("Jam Kerja") },
            isError = errorState.jamKerja != null
        )
        if (errorState.jamKerja != null) {
            Text(
                text = errorState.jamKerja,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}