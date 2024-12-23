package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dokter.HomeDokView
import com.example.ucp2.ui.view.dokter.InsertDokView
import com.example.ucp2.ui.view.jadwal.HomeJadwal
import com.example.ucp2.ui.view.jadwal.InsertJadwal
import com.example.ucp2.ui.view.jadwal.UpdateJadwalView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeDokView(

                onDetailClick = { idDokter ->
                    navController.navigate("${DestinasiDetail.route}/$idDokter")
                },
                onAddDokter = {
                    navController.navigate(DestinasiInsert.route)
                },
                onDetailJW = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route) {
            InsertDokView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier =modifier

            )
        }
        composable(route = DestinasiInsertJadwal.route) {
            InsertJadwal(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onBack = {
                    navController.popBackStack()
                },
                onDokter = {  },
                onJadwal = {

                }
            )
        }

        composable(route = DestinasiHomeJadwal.route) {
            HomeJadwal(
                onBackClick = {
                    navController.popBackStack()
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                onDetailJW = {
                    navController.navigate(DestinasiDetailJadwal.route)
                },
                onEditJadwal = {
                    navController.navigate("${DestinasiUpdateJadwal.route}/$it")
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiUpdateJadwal.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJadwal.idJadwal) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateJadwalView(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}