package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.RsApp


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                RsApp().containerApp.repositorydokter
            )
        }
        initializer {
            HomeScreenViewModel(
                RsApp().containerApp.repositorydokter
            )
        }
        initializer {
            InsertJadwalViewModel(
                RsApp().containerApp.repositoryjadwal,
                RsApp().containerApp.repositorydokter
            )
        }
        initializer {
            HomeJadwalViewModel(
                RsApp().containerApp.repositoryjadwal
            )
        }
        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                RsApp().containerApp.repositoryjadwal

            )
        }
        // Tambahkan ViewModel lain sesuai kebutuhan
    }
}

fun CreationExtras.RsApp(): RsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RsApp)