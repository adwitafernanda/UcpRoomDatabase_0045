package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.RsDatabase
import com.example.ucp2.repository.LocalRepositoryDok
import com.example.ucp2.repository.RepositoryDok

interface ContainerApp {
    val repositoryDok : RepositoryDok
}

class ContainerApp(private val context: Context) : ContainerApp {
    override val repositoryMhs: RepositoryDok by lazy {
        LocalRepositoryDok(RsDatabase.getDatabase(context).mahasiswaDao())
    }
}