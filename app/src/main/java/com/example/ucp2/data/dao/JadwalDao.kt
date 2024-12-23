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

    @Query("SELECT * FROM jadwal ORDER BY namaPasien ")
    fun getALLJadwal() : Flow<List<Jadwal>>

    @Query("SELECT * FROM jadwal WHERE idJadwal = :idJadwal")
    fun getJadwal(idJadwal: String) : Flow<Jadwal>

    @Query("""
        SELECT Jadwal.idJadwal, Jadwal.namaDokter, jadwal.namaPasien,Jadwal.noHp,Jadwal.tglKonsul,Jadwal.status
        FROM jadwal Jadwal
        INNER JOIN dokter d ON Jadwal.namaDokter = d.idDokter
        WHERE Jadwal.idJadwal = :idJadwal
        ORDER BY Jadwal.namaPasien ASC
    """)

    fun getJadwalJoin(idJadwal: String): Flow<List<Jadwal>>

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)
}