package com.example.ucp2

import android.app.Application
import com.example.ucp2.dependenciesinjection.ContainerApp

class RsApp : Application() {

    //fungsinya untuk menyimpan instance containerApp dari dependencies injection
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        // membuat instance containerApp
        containerApp = ContainerApp(this)
        // instance adalah object yang dibuat dari class4
    }
}