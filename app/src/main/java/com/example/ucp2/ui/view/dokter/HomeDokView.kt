package com.example.ucp2.ui.view.dokter

import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dokter
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import com.example.ucp2.ui.viewmodel.HomeScreenViewModel
import com.example.ucp2.ui.viewmodel.HomeUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import com.example.ucp2.R


@Composable
fun HomeDokView
    (
    viewModel: HomeScreenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onDetailClick: (String) -> Unit,
    onAddDokter: () -> Unit = {},
    onAddJadwal: () -> Unit = {},
    onDetailJW: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopHeader()
        },
        bottomBar = {
            SchedulesSection(onDetailJW=onDetailJW, onAddJadwal = onAddJadwal)
        }
    ) { padding ->

        val homeUiState by viewModel.homeUIState.collectAsState()
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BottomNavigationBar(onAddDokter = onAddDokter,onDetailJW= onDetailJW)
            TopDoctorsSection(
                listDokter = homeUiState.listDokter,
                onCLick = {
                    println(it)
                },
                modifier = Modifier.padding(top = 10.dp),
                onAddDokter = onAddDokter
            )
        }
    }
}

@Composable
fun TopHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xff08f281))
            .padding(16.dp)

    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = "RS MARIA",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black
                )
            )
        }
        // Kartu utama
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)

                .padding(16.dp)
        ) {

            // Konten di dalam kartu
            Row(
                modifier = Modifier.fillMaxSize()
                    .offset(y= 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto profil di sisi kanan
                Image(
                    painter = painterResource(id = R.drawable.adit),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCBD5E1))
                        .border(2.dp, color = Color.Gray, CircleShape),

                    contentScale = ContentScale.Crop
                )
                Spacer(
                    modifier = Modifier
                        .padding(30.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {

                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "Name : Adwita Fernanda ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Black
                            , fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic

                        )
                    )
                    Text(
                        text = "ID        : 133011450 ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Black
                            , fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic

                        )
                    )

                }

            }
        }
    }
}







@Composable
fun BottomNavigationBar(onAddDokter: () -> Unit,onDetailJW: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 1.dp)
            .offset(y = (-10).dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color(0xff08f281))
            .border(2.dp, Color(0xff08f281), RoundedCornerShape(15.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Navigate to Home */ }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onDetailJW) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Schedules List",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onAddDokter) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Doctor",
                    tint = Color.Black
                )
            }
        }
    }
}
@Composable
fun SchedulesSection(onDetailJW: () -> Unit,onAddJadwal:() -> Unit) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(95.dp),
        modifier = Modifier
            .offset(x = 18.dp, y = -25.dp)) {

        Button(
            onClick = onAddJadwal,
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff08f281),
                contentColor = Color.Black
            )
        ) {
            Text("Create Schedule")
        }
        Button(
            onClick = onDetailJW,
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff08f281),
                contentColor = Color.Black
            )
        ) {
            Text("View Schedules")
        }
    }
}


@Composable
fun BodyHomeDokterView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier,
    onAddDokter: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiState.isLoading -> {
            Box (modifier= modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            //menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let{message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)  //menampilkan snackbar
                    }
                }
            }
        }

        homeUiState.listDokter.isEmpty() -> {
            //menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data mahasiswa.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            //Menampilkan daftar mahasiswa
            TopDoctorsSection(
                listDokter = homeUiState.listDokter,
                onCLick = {
                    onClick(it)
                    println(it)
                },
                modifier = Modifier.padding(top = 10.dp),
                onAddDokter = onAddDokter
            )
        }
    }
}

@Composable
fun TopDoctorsSection(
    listDokter: List<Dokter>,
    onCLick: (String) -> Unit = { },
    modifier: Modifier = Modifier,
    onAddDokter: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "List Doctors",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = onAddDokter,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Doctor",
                tint = MaterialTheme.colorScheme.primary
            )
        }


        LazyColumn {
            items(items = listDokter, itemContent = { dokter ->
                // Dapatkan warna spesialisasi untuk dokter ini
                val spesialisColor = when (dokter.spesialis) {
                    "Umum" -> Color(0xFF06DCA6) // Red
                    "Anak" -> Color(0xFF73F664) // Blue
                    "Kandungan" -> Color(0xFFF3CE07) // Green
                    "Gigi" -> Color(0xFF656464) // Yellow
                    "Bedah" -> Color(0xFFD3020D) // Purple
                    else -> Color.Gray // Default color
                }

                DoctorCard(
                    name = dokter.namaDokter,
                    speciality = dokter.spesialis,
                    location = dokter.klinik,
                    workHours = dokter.jamKerja, // Menggunakan jam kerja
                    onClick = { onCLick(dokter.idDokter) },
                    color = spesialisColor // Menggunakan warna spesialisasi
                )
            })
        }
    }
}


@Composable
fun DoctorCard(
    name: String,
    speciality: String,
    location: String,
    workHours: String,
    onClick: () -> Unit,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable(onClick = onClick)
            .background(Color.White)
            .border(
                width = 1.dp,
                brush = Brush.radialGradient(
                    colors = listOf(Color.Black, Color.Gray),
                    center = Offset(50f, 50f),
                    radius = 100f
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .offset(x = 18.dp, y = 1.dp)
    ) {
        Image(
            imageVector = Icons.Filled.Person,
            contentDescription = name,
            modifier = Modifier
                .padding(top =5.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(color = color)
        )
        Spacer(modifier = Modifier.width(25.dp))
        Column(
            modifier = Modifier.weight(1f).padding(3.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = speciality,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
            Text(
                text = "Jam Kerja: $workHours",
                style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
            Text(
                text = "Klinik: $location",
                style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.width(17.dp))
        }
    }
}