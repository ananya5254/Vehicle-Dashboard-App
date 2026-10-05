package com.example.vehicleapp.presentation.vehicle.list



import com.example.vehicleapp.domain.model.Vehicle
import com.example.vehicleapp.domain.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadVehicles updates state when repository succeeds`() = runTest {
        val vehicles = listOf(
            Vehicle(
                id = "1",
                name = "Simple One",
                model = "Gen 2",
                batteryPercentage = 80,
                rangeKm = 180,
                currentSpeedKmh = 40,
                odometerKm = 12000,
                isOnline = true,
                lastUpdated = "04 Oct 2026, 18:26"
            )
        )

        val repository = FakeVehicleRepository(
            vehicles = vehicles
        )

        val viewModel = VehicleListViewModel(repository)

        viewModel.loadVehicles()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals(vehicles, state.vehicles)
        assertNull(state.error)
    }

    @Test
    fun `loadVehicles updates error when repository fails`() = runTest {
        val repository = FakeVehicleRepository(
            exception = Exception("Network error")
        )

        val viewModel = VehicleListViewModel(repository)

        viewModel.loadVehicles()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertTrue(state.error != null)
        assertEquals("Network error", state.error)
        assertEquals(emptyList<Vehicle>(), state.vehicles)
    }

    private class FakeVehicleRepository(
        private val vehicles: List<Vehicle> = emptyList(),
        private val exception: Exception? = null
    ) : VehicleRepository {

        override suspend fun getVehicles(): Result<List<Vehicle>> {
            return exception?.let {
                Result.failure(it)
            } ?: Result.success(vehicles)
        }

        override suspend fun getVehicle(id: String): Result<Vehicle> {
            return exception?.let {
                Result.failure(it)
            } ?: Result.success(
                vehicles.first { it.id == id }
            )
        }
    }
}