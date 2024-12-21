package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow


@Dao
interface DokterDao {

    //fungsi get all data
    @Query("SELECT * FROM dokter ORDER BY nama ASC")
    fun getAllDokter() : Flow<List<Dokter>>

    @Query("SELECT * FROM dokter WHERE id = :nim")  //mengambil data mahasiswa berdasarkan NIM
    fun getDokter(nim: String) : Flow<Dokter>

    @Delete
    suspend fun deleteDokter(dokter: Dokter)  //menghapus data mahasiswa tertentu dari database

    @Update
    suspend fun updateDokter(dokter: Dokter) //memperbarui informasi mahasiswa yang sudah ada di database

    @Insert
    suspend fun InsertDokter(dokter: Dokter)
}