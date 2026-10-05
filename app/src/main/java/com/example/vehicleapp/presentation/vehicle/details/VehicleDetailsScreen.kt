package com.example.vehicleapp.presentation.vehicle.details
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
fun VehicleDetailsScreen(
    vehicleId: String,
    viewModel: VehicleDetailsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(vehicleId) {
        viewModel.loadVehicle(vehicleId)
    }

    Scaffold(
        topBar = {
                TopAppBar(
                    title = {
                        Text("Vehicle Details")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBack
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                    onRetry = {
                        viewModel.refresh(vehicleId)
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }

            uiState.vehicle != null -> {
                VehicleDetails(
                    vehicle = uiState.vehicle!!,
                    onRefresh = {
                        viewModel.refresh(vehicleId)
                    },
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
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun VehicleDetails(
    vehicle: Vehicle,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        Text(
            text = vehicle.name
        )

        Text(
            text = vehicle.model
        )

        VehicleInfoRow(
            label = "Battery",
            value = "${vehicle.batteryPercentage}%"
        )

        VehicleInfoRow(
            label = "Estimated Range",
            value = "${vehicle.rangeKm} km"
        )

        VehicleInfoRow(
            label = "Current Speed",
            value = "${vehicle.currentSpeedKmh} km/h"
        )

        VehicleInfoRow(
            label = "Odometer",
            value = "${vehicle.odometerKm} km"
        )

        VehicleInfoRow(
            label = "Connectivity",
            value = if (vehicle.isOnline) "Online" else "Offline",
            valueColor = if (vehicle.isOnline) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.error
            }
        )

        VehicleInfoRow(
            label = "Last Updated",
            value = vehicle.lastUpdated
        )

        Button(
            onClick = onRefresh,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Refresh")
        }
    }
    }
}

@Composable
private fun VehicleInfoRow(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value, color = valueColor)
    }
}
