package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.viewmodel.HomeJadwalViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.HomeJadwalUiState
import kotlinx.coroutines.launch

@Composable
fun HomeJadwal(
    viewModel: HomeJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBackClick: () -> Unit,
    onAddJadwal: () -> Unit = {},
    onDetailJW: (String) -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val homeUiState by viewModel.homeJadwalUiState.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                onBack = onBackClick,
                judul = "Halaman Jadwal"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddJadwal,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Jadwal"
                )
            }
        },
        bottomBar = {
        }
    ) { innerPadding ->
        BodyHomeJadwalView(
            homeJadwalUiState = homeUiState,
            onEditJadwal = { onEditJadwal(it) },
            onClick = onDetailJW,
            modifier = Modifier.padding(innerPadding),
            viewModel= viewModel

        )
    }
}

@Composable
fun BodyHomeJadwalView(
    homeJadwalUiState: HomeJadwalUiState,
    onEditJadwal: (String) -> Unit = {},
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeJadwalViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var selectedJadwal: Jadwal? by remember { mutableStateOf(null) }

    when {
        homeJadwalUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeJadwalUiState.isError -> {
            LaunchedEffect(homeJadwalUiState.errorMessage) {
                homeJadwalUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeJadwalUiState.listJw.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada jadwal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(homeJadwalUiState.listJw) { jadwal ->
                    JadwalCard(
                        jadwal = jadwal,
                        onClick = { onClick(jadwal.idJadwal) },
                                onDelete = {
                            selectedJadwal = jadwal
                            deleteConfirmationRequired = true
                        },
                        onEditJadwal = { onEditJadwal(jadwal.idJadwal) }
                    )

                }
            }
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                selectedJadwal?.let { jadwal ->
                    coroutineScope.launch {
                        viewModel.deleteJadwal(jadwal)
                        snackbarHostState.showSnackbar("Jadwal berhasil dihapus")
                    }
                }
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }
        )
    }
}

@Composable
fun JadwalCard(
    jadwal: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Nama Dokter: ${jadwal.namaDokter}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Nama Pasien: ${jadwal.namaPasien}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tanggal Konsul: ${jadwal.tglKonsul}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Nomer Telp: ${jadwal.noHp}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Status: ${jadwal.status}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Tombol Edit dan Hapus
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
            ) {
                IconButton(
                    onClick = { onEditJadwal(jadwal.idJadwal) },
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .background(Color.Blue)
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit Jadwal",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(30.dp)
                    .padding(10.dp))
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .background(Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Jadwal",
                        tint = Color.White
                    )
                }
            }
        }
    }
}




@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = { Text(text = "Konfirmasi Hapus") },
        text = { Text(text = "Apakah Anda yakin ingin menghapus jadwal ini?") },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Hapus")
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        }
    )
}