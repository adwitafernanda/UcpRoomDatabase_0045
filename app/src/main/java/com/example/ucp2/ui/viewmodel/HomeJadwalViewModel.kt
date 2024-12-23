package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJadwal
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeJadwalViewModel(
    private val repositoryJadwal: RepositoryJadwal
): ViewModel() {
    val homeJadwalUiState: StateFlow<HomeJadwalUiState> = repositoryJadwal.getAllJadwal()
        .filterNotNull()
        .map {
                jadwalList->
            HomeJadwalUiState(
                listJw = jadwalList,
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeJadwalUiState(isLoading = true))
            delay(900)
        }
        .catch {
                exception ->
            emit(
                HomeJadwalUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeJadwalUiState(isLoading = true)
        )
    // Fungsi untuk menghapus jadwal
    fun deleteJadwal(jadwal: Jadwal) {
        viewModelScope.launch {
            try {
                repositoryJadwal.deleteJadwal(jadwal)
            } catch (e: Exception) {
                // Tangani error jika ada masalah dalam penghapusan
                println("Error deleting Jadwal: ${e.message}")
            }
        }
    }
}

data class HomeJadwalUiState(
    val listJw: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

fun Jadwal.toJadwalUiEvent():JadwalEvent{
    return JadwalEvent(
        idJadwal = idJadwal,
        namaDokter = namaDokter,
        namaPasien = namaPasien,
        noHp = noHp,
        tanggalKonsul = tglKonsul,
        status = status
    )
}