package com.example.vehicleapp.presentation.vehicle.list

import com.example.vehicleapp.domain.model.Vehicle

data class VehicleListUiState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(),
    val error: String? = null
)