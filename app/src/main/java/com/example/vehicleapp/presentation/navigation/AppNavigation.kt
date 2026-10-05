package com.example.vehicleapp.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vehicleapp.presentation.vehicle.details.VehicleDetailsScreen
import com.example.vehicleapp.presentation.vehicle.list.VehicleListScreen

private const val VEHICLE_LIST_ROUTE = "vehicles"
private const val VEHICLE_DETAILS_ROUTE = "vehicle/{vehicleId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = VEHICLE_LIST_ROUTE
    ) {
        composable(VEHICLE_LIST_ROUTE) {
            VehicleListScreen(
                viewModel = hiltViewModel(),
                onVehicleClick = { vehicleId ->
                    navController.navigate(
                        "vehicle/$vehicleId"
                    )
                }
            )
        }

        composable(
            route = VEHICLE_DETAILS_ROUTE,
            arguments = listOf(
                navArgument("vehicleId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val vehicleId = backStackEntry.arguments
                ?.getString("vehicleId")
                ?: return@composable

            VehicleDetailsScreen(
                vehicleId = vehicleId,
                viewModel = hiltViewModel(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}