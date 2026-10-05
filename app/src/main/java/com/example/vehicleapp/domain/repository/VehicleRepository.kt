package com.example.vehicleapp.domain.repository

import com.example.vehicleapp.domain.model.Vehicle

interface VehicleRepository {

    suspend fun getVehicles(): Result<List<Vehicle>>

    suspend fun getVehicle(id: String): Result<Vehicle>
}