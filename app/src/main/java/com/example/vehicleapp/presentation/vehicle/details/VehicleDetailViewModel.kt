package com.example.vehicleapp.presentation.vehicle.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.domain.model.Vehicle
import com.example.vehicleapp.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(VehicleDetailsUiState())

    val uiState: StateFlow<VehicleDetailsUiState> =
        _uiState.asStateFlow()

    fun loadVehicle(id: String) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            repository.getVehicle(id)
                .onSuccess { vehicle ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vehicle = vehicle,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                                ?: "Unable to load vehicle"
                        )
                    }
                }
        }
    }

    fun refresh(id: String) {
        loadVehicle(id)
    }
}