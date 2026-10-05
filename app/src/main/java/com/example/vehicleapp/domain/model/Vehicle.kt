package com.example.vehicleapp.domain.model

data class Vehicle(
    val id: String,
    val name: String,
    val model: String,
    val batteryPercentage: Int,
    val rangeKm: Int,
    val currentSpeedKmh: Int,
    val odometerKm: Int,
    val isOnline: Boolean,
    val lastUpdated: String
)
