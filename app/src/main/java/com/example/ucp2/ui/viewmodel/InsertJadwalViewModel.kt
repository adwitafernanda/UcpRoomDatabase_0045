package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal

import kotlinx.coroutines.launch

class InsertJadwalViewModel(
    private val repositoryJadwal: RepositoryJadwal,
    private val repositoryDokter: RepositoryDokter
) : ViewModel() {
    var jadwalUiState by mutableStateOf(JadwalUiState())

    init {
        getListDokter()
    }

    private fun getListDokter() {
        viewModelScope.launch {
            try {
                repositoryDokter.getAllDokter().collect { dokterList ->
                    jadwalUiState = jadwalUiState.copy(listDokter = dokterList)
                }
            } catch (e: Exception) {
                jadwalUiState = jadwalUiState.copy(snackBarMessage = "Gagal mengambil data dokter")
            }
        }
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        jadwalUiState = jadwalUiState.copy(
            jadwalEvent = jadwalEvent)
    }

    private fun validateFields(): Boolean {
        val event = jadwalUiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            idJadwal = if (event.idJadwal.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Tidak boleh kosong",
            tanggalKonsul = if (event.tanggalKonsul.isNotEmpty()) null else "Tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Tidak boleh kosong"
        )
        jadwalUiState = jadwalUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = jadwalUiState.jadwalEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.insertJadwal(currentEvent.toJadwalEntity())
                    jadwalUiState = jadwalUiState.copy(
                        snackBarMessage = "Data berhasil dikirim",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJadwal()
                    )
                } catch (e: Exception) {
                    jadwalUiState = jadwalUiState.copy(
                        snackBarMessage = "Gagal mengirim data: ${e.message}"
                    )
                }
            }
        } else {
            jadwalUiState = jadwalUiState.copy(
                snackBarMessage = "Input tidak valid, periksa kembali"
            )
        }
    }

    fun resetSnackBarMessage() {
        jadwalUiState = jadwalUiState.copy(snackBarMessage = null)
    }
}

data class JadwalUiState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJadwal = FormErrorStateJadwal(),
    val snackBarMessage: String? = null,
    val listDokter: List<Dokter> = emptyList()
)

data class FormErrorStateJadwal(
    val idJadwal: String? = null,
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsul: String? = null,
    val status: String? = null,
) {
    fun isValid(): Boolean {
        return idJadwal == null && namaDokter == null && namaPasien == null &&
                noHp == null && tanggalKonsul == null && status == null
    }
}

// Fungsi ekstensi untuk mengonversi JwEvent ke Jadwal
fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    idJadwal = idJadwal,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tglKonsul = tanggalKonsul,
    status = status
)

data class JadwalEvent(
    val idJadwal: String = "",
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsul: String = "",
    val status: String = "",
)