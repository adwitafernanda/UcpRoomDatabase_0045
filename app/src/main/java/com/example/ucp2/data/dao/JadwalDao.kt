package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {

    //fungsi get all data
    @Query("SELECT * FROM jadwal ORDER BY namadokter ASC")
    fun getAllMahasiswa() : Flow<List<Jadwal>>

    // get Mahasiswa
    @Query("SELECT * FROM jadwal WHERE id = :id")  //mengambil data mahasiswa berdasarkan NIM
    fun getMahasiswa(nim: String) : Flow<Jadwal>

    //Delete Mahasiswa
    @Delete
    suspend fun deleteMahasiswa(mahasiswa: Jadwal)  //menghapus data mahasiswa tertentu dari database

    //Update Mahasiswa
    @Update
    suspend fun updateMahasiswa(mahasiswa: Jadwal) //memperbarui informasi mahasiswa yang sudah ada di database

    @Insert
    suspend fun InsertMahasiswa(mahasiswa: Jadwal)
}