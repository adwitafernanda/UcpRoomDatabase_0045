package com.example.ucp2.repository

import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJadwal (
    private val jadwalDao: JadwalDao
): RepositoryJadwal {
    override suspend fun insertJadwal(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }

    override suspend fun deleteJadwal(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }

    override suspend fun updateJadwal(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }

    override fun getAllJadwal(): Flow<List<Jadwal>> {
        return jadwalDao.getALLJadwal()
    }

    override fun getJadwal(idJadwal: String): Flow<Jadwal?> {
        return jadwalDao.getJadwal(idJadwal)
    }

    override fun getJadwalJoin(idJadwal: String): Flow<List<Jadwal>>{
        return jadwalDao.getJadwalJoin((idJadwal))
    }
}