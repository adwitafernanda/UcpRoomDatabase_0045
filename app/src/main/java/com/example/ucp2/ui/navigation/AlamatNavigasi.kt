package com.example.ucp2.ui.navigation

interface AlamatNavigasi  {
    val route: String
}

object DestinasiInsert : AlamatNavigasi { // Object untuk halaman insert dokter
    override val route: String = "insertdokter"
}

object DestinasiHome : AlamatNavigasi { // Object untuk halaman home
    override val route: String = "home"
}

object DestinasiInsertJadwal : AlamatNavigasi {
    override val route: String = "insertjadwal"
}

object  DestinasiHomeJadwal : AlamatNavigasi {
    override val route: String = "home_jadwal"
}

object DestinasiDetailJadwal : AlamatNavigasi {
    override val route = "detail_jadwal"
    const val idJadwal = "idJadwal"
    val routesWithArg = "$route/{$idJadwal}"
}

object DestinasiUpdateJadwal : AlamatNavigasi {
    override val route = " update_jadwal"
    const val  idJadwal = " idJadwal"
    val routesWithArg = "$route/{$idJadwal}"
}

object DestinasiDetail : AlamatNavigasi { // Object untuk halaman detail dokter
    override val route: String = "detail"
    const val ID_DOKTER = "idDokter"
    val routesWithArg = "$route/{$ID_DOKTER}"
}