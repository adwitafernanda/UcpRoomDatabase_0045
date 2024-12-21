package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow


@Dao
interface DokterDao {


    @Query("SELECT * FROM dokter WHERE id = :id")
    fun getDokter(id: String) : Flow<Dokter>

    @Insert
    suspend fun InsertDokter(dokter: Dokter)
}
