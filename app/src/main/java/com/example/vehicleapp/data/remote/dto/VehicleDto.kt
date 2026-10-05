package com.example.vehicleapp.data.remote.dto

import com.example.vehicleapp.domain.model.Vehicle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class VehicleDto(
    val id: String,
    val name: String,
    val model: String,
    val batteryPercentage: Int,
    val rangeKm: Int,
    val currentSpeedKmh: Int,
    val odometerKm: Int,
    val isOnline: Boolean,
    val lastUpdated: Long
)

fun VehicleDto.toDomain(): Vehicle {
    val formattedLastUpdated = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    ).format(Date(lastUpdated * 1000))

    return Vehicle(
        id = id,
        name = name,
        model = model,
        batteryPercentage = batteryPercentage,
        rangeKm = rangeKm,
        currentSpeedKmh = currentSpeedKmh,
        odometerKm = odometerKm,
        isOnline = isOnline,
        lastUpdated = formattedLastUpdated
    )
}