package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jadwal")
data class Jadwal(
    @PrimaryKey
    val id: String,
    val namadokter: String,
    val namapasien: String,
    val nohp: String,
    val tglkonsul: String,
    val status: String,
)