package com.example.vehicleapp.di

import com.example.vehicleapp.data.repository.VehicleRepositoryImpl
import com.example.vehicleapp.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVehicleRepository(
        repository: VehicleRepositoryImpl
    ): VehicleRepository
}