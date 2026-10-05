package com.example.vehicleapp.presentation.vehicle.list
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vehicleapp.domain.model.Vehicle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    viewModel: VehicleListViewModel,
    onVehicleClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadVehicles()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Vehicles")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->

        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            uiState.error != null -> {
                ErrorContent(
                    message = uiState.error ?: "Something went wrong",
                    onRetry = viewModel::refresh,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            uiState.vehicles.isEmpty() -> {
                EmptyContent(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            else -> {
                VehicleList(
                    vehicles = uiState.vehicles,
                    onVehicleClick = onVehicleClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No vehicles found")
    }
}
@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(message)

            androidx.compose.material3.Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text("Retry")
            }
        }
    }
}
@Composable
private fun VehicleList(
    vehicles: List<Vehicle>,
    onVehicleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = vehicles,
            key = { it.id }
        ) { vehicle ->

            VehicleCard(
                vehicle = vehicle,
                onClick = {
                    onVehicleClick(vehicle.id)
                }
            )
        }
    }
}
