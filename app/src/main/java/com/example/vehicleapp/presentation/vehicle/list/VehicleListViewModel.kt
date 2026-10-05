package com.example.vehicleapp.presentation.vehicle.list
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
class VehicleListViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(VehicleListUiState())

    val uiState: StateFlow<VehicleListUiState> =
        _uiState.asStateFlow()

    fun loadVehicles() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            repository.getVehicles()
                .onSuccess { vehicles ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vehicles = vehicles,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                                ?: "Unable to load vehicles"
                        )
                    }
                }
        }
    }

    fun refresh() {
        loadVehicles()
    }
}