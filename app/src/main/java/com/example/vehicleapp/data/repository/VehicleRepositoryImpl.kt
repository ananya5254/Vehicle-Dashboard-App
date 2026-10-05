package com.example.vehicleapp.data.repository

import com.example.vehicleapp.data.remote.VehicleService
import com.example.vehicleapp.data.remote.dto.toDomain
import com.example.vehicleapp.domain.model.Vehicle
import com.example.vehicleapp.domain.repository.VehicleRepository
import javax.inject.Inject


class VehicleRepositoryImpl @Inject constructor(
    private val vehicleService: VehicleService
) : VehicleRepository {

    override suspend fun getVehicles(): Result<List<Vehicle>> {
        return try {
            val vehicles = vehicleService
                .getVehicles()
                .map { it.toDomain() }

            Result.success(vehicles)

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getVehicle(
        id: String
    ): Result<Vehicle> {
        return try {
            Result.success(
                vehicleService
                    .getVehicle(id)
                    .toDomain()
            )

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
