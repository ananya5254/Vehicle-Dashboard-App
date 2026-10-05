package com.example.vehicleapp.data.remote


import com.example.vehicleapp.data.remote.dto.VehicleDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VehicleService {

    @GET("vehicles")
    suspend fun getVehicles(): List<VehicleDto>

    @GET("vehicles/{id}")
    suspend fun getVehicle(
        @Path("id") id: String
    ): VehicleDto
}