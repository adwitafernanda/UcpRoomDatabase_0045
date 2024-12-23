package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.ui.navigation.DestinasiUpdateJadwal
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private  val repositoryJadwal: RepositoryJadwal
): ViewModel()
{
    var updateUiState by mutableStateOf(JadwalUiState())
        private set

    private val _idJadwal: String = checkNotNull(savedStateHandle[DestinasiUpdateJadwal.idJadwal])

    init {
        viewModelScope.launch {
            updateUiState = repositoryJadwal.getJadwal(_idJadwal)
                .filterNotNull()
                .first()
                .toUIStateJadwal()
        }
    }

    fun updateState(jadwalEvent: JadwalEvent){
        updateUiState = updateUiState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            idJadwal = if (event.idJadwal.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Tidak boleh kosong",
            tanggalKonsul = if (event.tanggalKonsul.isNotEmpty()) null else "Tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Tidak boleh kosong"
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(namaDokter: String) {
        val currentEvent = updateUiState.jadwalEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {

                    repositoryJadwal.updateJadwal(currentEvent.toJadwalEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        jadwalEvent = JadwalEvent(), // Reset event setelah update
                        isEntryValid = FormErrorStateJadwal()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal di update"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }

}

fun Jadwal.toUIStateJadwal(): JadwalUiState = JadwalUiState(
    jadwalEvent = this.toJadwalUiEvent()
)
