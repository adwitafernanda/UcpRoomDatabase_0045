package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.RsDatabase
import com.example.ucp2.repository.LocalRepositoryDokter
import com.example.ucp2.repository.LocalRepositoryJadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal


interface InterfaceContainerApp {
    val repositorydokter: RepositoryDokter
    val repositoryjadwal: RepositoryJadwal
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositorydokter: RepositoryDokter by lazy {
        LocalRepositoryDokter(RsDatabase.getDatabase(context).dokterDao())
    }


    override val repositoryjadwal: RepositoryJadwal by lazy {
        LocalRepositoryJadwal(RsDatabase.getDatabase(context).jadwalDao())
    }
}

