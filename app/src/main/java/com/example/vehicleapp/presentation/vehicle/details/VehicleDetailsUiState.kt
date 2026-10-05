package com.example.vehicleapp.presentation.vehicle.details

import com.example.vehicleapp.domain.model.Vehicle

data class VehicleDetailsUiState(
    val isLoading: Boolean = false,
    val vehicle: Vehicle? = null,
    val error: String? = null
)