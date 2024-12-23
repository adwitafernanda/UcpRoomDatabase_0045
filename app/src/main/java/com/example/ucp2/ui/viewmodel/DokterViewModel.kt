package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDokter
import kotlinx.coroutines.launch

class DokterViewModel(
    private val repositoryDokter: RepositoryDokter
) : ViewModel() {


    private val _dokterEvent = MutableLiveData<List<Dokter>>() // Menggunakan LiveData untuk memantau perubahan
    val dokterEvent: LiveData<List<Dokter>> get() = _dokterEvent

    // Fungsi untuk memperbarui daftar dokter
    fun setDokterList(dokters: List<Dokter>) {
        _dokterEvent.value = dokters
    }

    // Fungsi untuk mendapatkan daftar dokter
    fun getDokterList(): List<Dokter> {
        return _dokterEvent.value ?: emptyList()
    }

    var uiState by mutableStateOf(DokterUIState())
        private set

    fun updateState(dokterEvent: DokterEvent) {
        uiState = uiState.copy(
            dokterEvent = dokterEvent
        )
    }


    private fun validateFields(): Boolean{
        val event = uiState.dokterEvent
        val errorState = FormErrorState(
            idDokter = if (event.idDokter.isNotEmpty()) null else " iddokter tida boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else " nama tidak boleh kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "pilih spesialis",
            klinik = if (event.klinik.isNotEmpty()) null else " klinik tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else " no hp harus di isi",
            jamKerja = if (event.jamKerja.isNotEmpty()) null else "jam kerja harus di isi"
        )

        uiState = uiState.copy(isEntryValid = errorState )
        return  errorState.isValid()
    }


    // Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.dokterEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDokter.insertDokter(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dokterEvent = DokterEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan: ${e.message}"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "input tidak valid periksa kembali data anda"
            )
        }
    }
    //reset pesan sncakbar setelah di tampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}



data class DokterUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)


data class DokterEvent(
    val idDokter: String = "",
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHp: String = "",
    val jamKerja: String = ""
){
    fun toDokterEntity(): Dokter = Dokter(
        idDokter = idDokter,
        namaDokter = nama,
        spesialis = spesialis,
        klinik = klinik,
        noHp = noHp,
        jamKerja = jamKerja
    )

}

data class FormErrorState(
    val idDokter: String? =null,
    val nama: String? =null,
    val spesialis: String? =null,
    val klinik: String? =null,
    val noHp: String? =null,
    val jamKerja: String? =null
){fun isValid(): Boolean {
    return idDokter == null &&
            nama == null &&
            spesialis == null &&
            klinik == null &&
            noHp == null &&
            jamKerja == null
}
}